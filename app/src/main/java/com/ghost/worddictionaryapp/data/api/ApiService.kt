package com.ghost.worddictionaryapp.data.api

import com.ghost.worddictionaryapp.data.model.RandomWordModel
import com.ghost.worddictionaryapp.data.model.WordMeaningItemModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    //Get Random Word
    @GET(ApiReference.WORD_ENDPOINT)
    suspend fun getRandomWord(): Response<RandomWordModel>

    //Get Word Meaning
    @GET("entries/en/{word}")
    suspend fun getWordMeaning(
        @Path("word") word: String
    ): Response<ArrayList<WordMeaningItemModel>>
}