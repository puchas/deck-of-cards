package pl.puchalski.deckofcards.cards.model

import com.google.gson.annotations.SerializedName

data class CardsContainer(
	val success: Boolean,
	val cards: List<Card>?,
	@SerializedName("deck_id")
	val deckId: String,
	val remaining: Int
)