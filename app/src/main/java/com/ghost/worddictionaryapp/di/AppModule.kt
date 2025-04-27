package com.ghost.worddictionaryapp.di

import com.ghost.worddictionaryapp.data.BASE_URL_1
import com.ghost.worddictionaryapp.data.BASE_URL_2
import com.ghost.worddictionaryapp.data.api.ApiService
import com.ghost.worddictionaryapp.data.repository.WordRepository
import com.ghost.worddictionaryapp.data.repository.WordRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RandomWordRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WordMeaningRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RandomWordApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WordMeaningApi


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    //Provides Coroutine Dispatcher
    @Provides
    @Singleton
    fun providesCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    //Provides Gson Converter
    @Provides
    @Singleton
    fun providesGson(): GsonConverterFactory = GsonConverterFactory.create()

    //Provides Random Word Retrofit

   @Provides
   @Singleton
   @RandomWordRetrofit
    fun providesRandomWordRetrofit(
        gson: GsonConverterFactory
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL_1)
        .addConverterFactory(gson)
        .build()

    //Provides Word Meaning Retrofit

    @Provides
    @Singleton
    @WordMeaningRetrofit
    fun providesWordMeaningRetrofit(
        gson: GsonConverterFactory
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL_2)
        .addConverterFactory(gson)
        .build()

    //Provides Random Word Api Service
    @Provides
    @Singleton
    @RandomWordApi
    fun providesRandomWordApiService(
      @RandomWordRetrofit randomWordRetrofit: Retrofit
    ): ApiService = randomWordRetrofit.create(ApiService::class.java)

  //Provides Word Meaning Api Service
    @Provides
    @Singleton
  @WordMeaningApi
    fun providesWordMeaningApiService(
      @WordMeaningRetrofit wordMeaningRetrofit: Retrofit
    ): ApiService = wordMeaningRetrofit.create(ApiService::class.java)

 //Provides Word Repository
    @Provides
    @Singleton
 fun providesWordRepository(
  @RandomWordApi randomWordApiService: ApiService,
   @WordMeaningApi wordMeaningApiService: ApiService
 ): WordRepository = WordRepositoryImpl(randomWordApiService, wordMeaningApiService)

}