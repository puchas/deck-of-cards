package pl.puchalski.deckofcards.cards.repository

import pl.puchalski.deckofcards.cards.model.CardsContainer
import pl.puchalski.deckofcards.cards.model.Deck
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CardsApi {
	@GET("new/shuffle")
	fun shuffleDeck(@Query("deck_count") deckCount: Int): Deck

	@GET("{deckId}/draw")
	fun drawCards(
		@Path("deckId") deckId: String,
		@Query("count") cardCount: Int
	): CardsContainer
}