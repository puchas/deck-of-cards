package pl.puchalski.deckofcards.config

import org.koin.dsl.module
import pl.puchalski.deckofcards.cards.repository.CardsApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://deckofcardsapi.com/api/deck/"

val retrofitModule = module {
	single {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}
	single<CardsApi> { createWebService(get()) }
}

inline fun <reified T> createWebService(retrofit: Retrofit): T {
	return retrofit.create(T::class.java)
}