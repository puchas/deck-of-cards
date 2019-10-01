package pl.puchalski.deckofcards.main

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import pl.puchalski.deckofcards.R
import pl.puchalski.deckofcards.game.GameActivity

class MainActivity : AppCompatActivity() {

	private val options = arrayOf(1, 2, 3, 4, 5)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setupSpinner()
		setupPlayButton()
	}

	private fun setupSpinner() {
		val spinnerAdapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, options)
		spinner_deck_count.adapter = spinnerAdapter
	}

	private fun setupPlayButton() {
		btn_play.setOnClickListener {
			val selectedPosition = spinner_deck_count.selectedItemPosition
			val deckCount = options[selectedPosition]
			GameActivity.startGame(this, deckCount)
		}
	}
}