package pl.puchalski.deckofcards.game.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pl.puchalski.deckofcards.R
import pl.puchalski.deckofcards.cards.model.Card

class CardsAdapter : RecyclerView.Adapter<CardsViewHolder>() {

	var cards: List<Card> = listOf()
	private val picasso = Picasso.get()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
		val itemView = LayoutInflater.from(parent.context).inflate(
			R.layout.item_card,
			parent,
			false
		)
		return CardsViewHolder(itemView, picasso)
	}

	override fun getItemCount() = cards.size

	override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
		holder.bind(cards[position])
	}

	fun updateCards(cards: List<Card>) {
		this.cards = cards
		notifyDataSetChanged()
	}
}