package com.ghost.worddictionaryapp.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghost.worddictionaryapp.data.model.WordMeaningItemModel
import com.ghost.worddictionaryapp.data.repository.WordRepository
import com.ghost.worddictionaryapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _currentWordState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val currentWordState: StateFlow<UiState<String>> = _currentWordState

    private val _meaningState = MutableStateFlow<UiState<ArrayList<WordMeaningItemModel>>>(UiState.Loading)
    val meaningState: StateFlow<UiState<ArrayList<WordMeaningItemModel>>> = _meaningState

    private val wordHistory = mutableListOf<String>()
    private var currentWordIndex = -1

    init {
        fetchRandomWordAndMeaning()
    }

    fun fetchRandomWordAndMeaning() {
        viewModelScope.launch(dispatcher) {
            wordRepository.fetchRandomWord().collectLatest { uiState ->
                when (uiState) {
                    is UiState.Loading -> _currentWordState.value = UiState.Loading
                    is UiState.Success -> {
                        val response = uiState.data
                        if (response.isSuccessful && response.body() != null) {
                            val randomWordList = response.body()
                            val fetchedWord = randomWordList?.firstOrNull() ?: ""
                            _currentWordState.value = UiState.Success(fetchedWord)
                            wordHistory.add(fetchedWord)
                            currentWordIndex = wordHistory.lastIndex
                            fetchMeaning(fetchedWord)
                        } else {
                            _currentWordState.value = UiState.Error("Failed to fetch word: ${response.errorBody()?.string()}")
                            _meaningState.value = UiState.Error("No meaning available")
                        }
                    }
                    is UiState.Error -> {
                        _currentWordState.value = UiState.Error(uiState.message)
                        _meaningState.value = UiState.Error("No meaning available due to word fetch error")
                    }
                }
            }
        }
    }

    fun fetchMeaning(word: String) {
        _meaningState.value = UiState.Loading
        viewModelScope.launch(dispatcher) {
            wordRepository.fetchWordMeaning(word).collectLatest { uiState ->
                when (uiState) {
                    is UiState.Loading -> _meaningState.value = UiState.Loading
                    is UiState.Success -> {
                        val meaning = uiState.data
                        _meaningState.value = UiState.Success(meaning)
                    }
                    is UiState.Error -> _meaningState.value = UiState.Error(uiState.message)
                }
            }
        }
    }

    fun nextWord() {
        if (currentWordIndex < wordHistory.lastIndex) {
            currentWordIndex++
            _currentWordState.value = UiState.Success(wordHistory[currentWordIndex])
            fetchMeaning(wordHistory[currentWordIndex])
        } else {
            fetchRandomWordAndMeaning()
        }
    }

    fun previousWord() {
        if (currentWordIndex > 0) {
            currentWordIndex--
            _currentWordState.value = UiState.Success(wordHistory[currentWordIndex])
            fetchMeaning(wordHistory[currentWordIndex])
        }
    }
}