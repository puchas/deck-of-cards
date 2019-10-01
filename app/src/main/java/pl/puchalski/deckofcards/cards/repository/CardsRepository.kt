package pl.puchalski.deckofcards.cards.repository

class CardsRepository(private val service: CardsApi) : CardsDataSource {
	override fun shuffleDeck(deckCount: Int) = service.shuffleDeck(deckCount)
	override fun drawCard(deckId: String, cardCount: Int) = service.drawCards(deckId, cardCount)
}