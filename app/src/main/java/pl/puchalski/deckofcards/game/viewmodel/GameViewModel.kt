package pl.puchalski.deckofcards.game.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.puchalski.deckofcards.cards.common.BaseViewModel
import pl.puchalski.deckofcards.cards.model.Card
import pl.puchalski.deckofcards.cards.model.Deck
import pl.puchalski.deckofcards.cards.repository.CardsDataSource

class GameViewModel(private val cardsRepository: CardsDataSource) : BaseViewModel() {
	val cardsLeft = MutableLiveData<Int>()
	val cards = MutableLiveData<List<Card>>()
	val finishGameEvent = MutableLiveData<FinishGameEvent>()
	private val deck = MutableLiveData<Deck>()

	fun loadDeck(deckCount: Int) {
		cards.value = listOf()
		cardsRepository.shuffleDeck(deckCount)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				{
					cardsLeft.postValue(it.remaining)
					deck.postValue(it)
				}, {
					Log.e(this::class.java.simpleName, it.localizedMessage, it)
				}
			).let {
				addToCompositeDisposable(it)
			}
	}

	fun drawCards() {
		val currentCardsLeft = cardsLeft.value ?: 0
		if (currentCardsLeft - CARDS_TO_DRAW < 0) {
			finishGameEvent.postValue(FinishGameEvent.NoCardsLeft)
			return
		}
		val deckId = deck.value?.deckId
		deckId?.let {
			cardsRepository.drawCard(deckId, CARDS_TO_DRAW)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
					{
						it.cards?.let { cards -> checkWin(cards) }
						cardsLeft.postValue(it.remaining)
						cards.postValue(it.cards)
					}, {
						Log.e(this::class.java.simpleName, it.localizedMessage, it)
					}
				).let {
					addToCompositeDisposable(it)
				}
		}
	}

	private fun checkWin(cards: List<Card>) {
		when {
			checkTwins(cards) -> finishGameEvent.postValue(FinishGameEvent.Win(WinScenario.Twins))
			checkStairs(cards) -> finishGameEvent.postValue(FinishGameEvent.Win(WinScenario.Stairs))
			checkFigures(cards) ->
				finishGameEvent.postValue(FinishGameEvent.Win(WinScenario.Figures))
			checkColor(cards) -> finishGameEvent.postValue(FinishGameEvent.Win(WinScenario.Color))
		}
	}

	private fun checkTwins(cards: List<Card>): Boolean {
		val cardValues = cards.groupBy { it.value }
		return cardValues.values.any { it.size >= 3 }
	}

	private fun checkStairs(cards: List<Card>): Boolean {
		return cards.any { card ->
			val cardNumericValue = cardNumericValue(card.value)
			val cardNumericValues = cards.map { cardNumericValue(it.value) }
			val stairsDecreasing = listOf(cardNumericValue - 1, cardNumericValue - 2)
			val stairsIncreasing = listOf(cardNumericValue + 1, cardNumericValue + 2)
			cardNumericValues.containsAll(stairsDecreasing) or
					cardNumericValues.containsAll(stairsIncreasing)
		}
	}

	private fun cardNumericValue(value: String): Int {
		return when (val intValue = value.toIntOrNull()) {
			null -> when (value) {
				JACK -> 11
				QUEEN -> 12
				KING -> 13
				else -> 1
			}
			0 -> 10
			else -> intValue
		}
	}

	private fun checkFigures(cards: List<Card>): Boolean {
		val figures = listOf(JACK, QUEEN, KING)
		var figuresCount = 0
		cards.forEach { if (it.value in figures) figuresCount++ }
		return figuresCount >= 3
	}

	private fun checkColor(cards: List<Card>): Boolean {
		val cardColors = cards.groupBy { it.suit }
		return cardColors.values.any { it.size >= 3 }
	}

	sealed class FinishGameEvent {
		object NoCardsLeft : FinishGameEvent()
		class Win(val winScenario: WinScenario) : FinishGameEvent()
	}

	sealed class WinScenario {
		object Twins : WinScenario()
		object Stairs : WinScenario()
		object Figures : WinScenario()
		object Color : WinScenario()
	}

	companion object {
		const val CARDS_TO_DRAW = 5
		const val JACK = "JACK"
		const val QUEEN = "QUEEN"
		const val KING = "KING"
	}
}