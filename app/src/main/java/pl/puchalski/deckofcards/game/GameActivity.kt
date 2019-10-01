package pl.puchalski.deckofcards.game

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

	private fun restoreDeckCount(): Int {
		val deckCount = intent?.extras?.getInt(DECK_COUNT)
		if (null == deckCount) {
			throw IllegalArgumentException("Use method startGame to start GameActivity")
		} else {
			return deckCount
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