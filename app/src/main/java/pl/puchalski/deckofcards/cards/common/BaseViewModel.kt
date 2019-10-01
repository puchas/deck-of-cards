package pl.puchalski.deckofcards.cards.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
	private val compositeDisposable = CompositeDisposable()

	override fun onCleared() {
		super.onCleared()
		compositeDisposable.dispose()
	}

	fun addToCompositeDisposable(disposable: Disposable) {
		compositeDisposable.add(disposable)
	}
}