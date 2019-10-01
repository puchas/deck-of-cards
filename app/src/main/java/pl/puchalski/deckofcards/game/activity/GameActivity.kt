package pl.puchalski.deckofcards.game.activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game.*
import pl.puchalski.deckofcards.R
import pl.puchalski.deckofcards.game.adapter.CardsAdapter

class GameActivity : AppCompatActivity() {

	private val adapter by lazy { CardsAdapter() }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_game)
		setupRecyclerView()
	}

	private fun restoreDeckCount(): Int {
		val deckCount = intent?.extras?.getInt(DECK_COUNT)
		if (null == deckCount) {
			throw IllegalArgumentException("Use method startGame to start GameActivity")
		} else {
			return deckCount
		}
	}

	private fun setupRecyclerView() {
		val orientation = resources.configuration.orientation
		rv_cards.let {
			it.adapter = adapter
			it.layoutManager = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
				LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
			} else {
				GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
			}
		}
	}

	companion object {
		private const val DECK_COUNT = "DECK_COUNT"
		fun startGame(context: Context, deckCount: Int) {
			val intent = Intent(context, GameActivity::class.java)
			intent.putExtra(DECK_COUNT, deckCount)
			context.startActivity(intent)
		}
	}
}