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
	private val deck = MutableLiveData<Deck>()

	fun loadDeck(deckCount: Int) {
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
		val deckId = deck.value?.deckId
		deckId?.let {
			cardsRepository.drawCard(deckId, CARDS_TO_DRAW)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
					{
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

	companion object {
		const val CARDS_TO_DRAW = 5
	}
}