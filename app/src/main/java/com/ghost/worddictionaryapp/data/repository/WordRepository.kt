package com.ghost.worddictionaryapp.data.repository

import com.ghost.worddictionaryapp.data.model.RandomWordModel
import com.ghost.worddictionaryapp.data.model.WordMeaningItemModel
import com.ghost.worddictionaryapp.utils.UiState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface WordRepository {
    //Get Random Word
    suspend fun fetchRandomWord() : Flow<UiState<Response<RandomWordModel>>>

    //Get Word Meaning
    suspend fun fetchWordMeaning(word:String): Flow<UiState<ArrayList<WordMeaningItemModel>>>
}