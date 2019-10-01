package pl.puchalski.deckofcards.game.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_card.view.*
import pl.puchalski.deckofcards.cards.model.Card

class CardsViewHolder(
	itemView: View,
	private val picasso: Picasso
) : RecyclerView.ViewHolder(itemView) {
	fun bind(card: Card) {
		picasso.load(card.image).into(itemView.iv_card)
	}
}