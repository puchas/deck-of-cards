package pl.puchalski.deckofcards.config

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import pl.puchalski.deckofcards.cards.repository.CardsDataSource
import pl.puchalski.deckofcards.cards.repository.CardsRepository
import pl.puchalski.deckofcards.game.viewmodel.GameViewModel

class CustomApplication : Application() {
	private val appModule = module {
		single<CardsDataSource> { CardsRepository(get()) }
		viewModel { GameViewModel(get()) }
	}

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidLogger()
			androidContext(this@CustomApplication)
			modules(appModule + retrofitModule)
		}
	}
}