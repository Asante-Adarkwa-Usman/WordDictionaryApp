package com.ghost.worddictionaryapp.data.repository

import android.util.Log
import com.ghost.worddictionaryapp.data.api.ApiService
import com.ghost.worddictionaryapp.data.model.RandomWordModel
import com.ghost.worddictionaryapp.data.model.WordMeaningItemModel
import com.ghost.worddictionaryapp.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class WordRepositoryImpl@Inject constructor(
   private val randomWordApiService: ApiService,
    private val wordMeaningApiService: ApiService
): WordRepository {

    override suspend fun fetchRandomWord(): Flow<UiState<Response<RandomWordModel>>> = flow {
       emit(UiState.Loading)
        try {
            val response = randomWordApiService.getRandomWord()
            if (response.isSuccessful) {
                Log.e("Word", "Random word received: ${response.body().toString()}")
                emit(UiState.Success(response))
            } else {
                emit(UiState.Error("Unable to fetch random word"))
                Log.e("Word Meaning", response.message())
            }
        } catch (e:Exception){
            emit(UiState.Error(e.message.toString()))
            Log.e("Word Meaning", e.message.toString())

        }
    }

    override suspend fun fetchWordMeaning(word: String): Flow<UiState<ArrayList<WordMeaningItemModel>>> = flow {
        emit(UiState.Loading)
        try {
            val response = wordMeaningApiService.getWordMeaning(word)
            if (response.isSuccessful) {
                val body = response.body() // The body is expected to be a List of WordMeaningItemModel (WordMeaning)
                if (!body.isNullOrEmpty()) {
                    emit(UiState.Success(body))  // Emit the List of WordMeaningItemModel
                    Log.e("Word Meaning", "Word meanings fetched successfully")
                } else {
                    emit(UiState.Error("No word meanings found"))
                    Log.e("Word Meaning", "Empty response body")
                }
            } else {
                // Convert error body to string for logging purposes
                val errorBody = response.errorBody()?.string() ?: "Unknown error body"
                emit(UiState.Error("Sorry pal, we couldn't find definitions for the word: $word"))
                Log.e("Word Meaning", "Error Body: $errorBody")
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "Unknown error occurred"))
            Log.e("Word Meaning Exception", e.message ?: "Unknown error")
        }
    }



}