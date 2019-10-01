package pl.puchalski.deckofcards.cards.model

data class Card(
	val image: String,
	val value: String,
	val suit: String,
	val code: String
)