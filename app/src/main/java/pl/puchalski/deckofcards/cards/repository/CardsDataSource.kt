package pl.puchalski.deckofcards.cards.repository

import io.reactivex.Observable
import pl.puchalski.deckofcards.cards.model.CardsContainer
import pl.puchalski.deckofcards.cards.model.Deck

interface CardsDataSource {
	fun shuffleDeck(deckCount: Int): Observable<Deck>
	fun drawCard(deckId: String, cardCount: Int): Observable<CardsContainer>
}