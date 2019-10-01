package pl.puchalski.deckofcards.cards.repository

import pl.puchalski.deckofcards.cards.model.CardsContainer
import pl.puchalski.deckofcards.cards.model.Deck

interface CardsDataSource {
	fun shuffleDeck(deckCount: Int): Deck
	fun drawCard(deckId: String, cardCount: Int): CardsContainer
}