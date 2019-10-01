package pl.puchalski.deckofcards.cards.model

import com.google.gson.annotations.SerializedName

data class Deck(
	val success: Boolean,
	@SerializedName("deck_id")
	val deckId: String,
	val shuffled: Boolean,
	val remaining: Int
)