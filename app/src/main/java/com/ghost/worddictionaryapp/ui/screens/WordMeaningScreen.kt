package com.ghost.worddictionaryapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ghost.worddictionaryapp.ui.vm.WordViewModel
import com.ghost.worddictionaryapp.ui.widgets.WordCard
import com.ghost.worddictionaryapp.utils.UiState

@Composable
fun WordMeaningScreen(viewModel: WordViewModel = hiltViewModel()) {
    val currentWordState by viewModel.currentWordState.collectAsState()
    val meaningState by viewModel.meaningState.collectAsState()

    // Simplified loading and error UI for reusability
    @Composable
    fun loadingUI() = Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }

    @Composable
    fun errorUI(message: String, retryAction: () -> Unit) = Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = retryAction,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Retry")
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (val currentState = currentWordState) {
                is UiState.Loading -> {
                    // Show loading state for the word
                    loadingUI()
                }
                is UiState.Success -> {
                    val word = currentState.data
                    when (val meaningStateValue = meaningState) {
                        is UiState.Loading -> {
                            // Show loading state for meanings
                            loadingUI()
                        }
                        is UiState.Success -> {
                            val meanings = meaningStateValue.data
                            if (meanings.isNotEmpty()) {
                                WordCard(
                                    word = word,
                                    meanings = meanings.first().meanings, // Get the list of meanings
                                    onSwipeLeft = { viewModel.previousWord() },
                                    onSwipeRight = { viewModel.nextWord() }
                                )
                            } else {
                                Text(text = "No meaning found for $word")
                            }
                        }
                        is UiState.Error -> {
                            // Show error state for meanings
                            errorUI(message = "Error fetching meaning: ${meaningStateValue.message}") {
                                viewModel.nextWord() // Retry fetching the meaning
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    // Handle error state for the word
                    errorUI(message = "Error fetching word: ${currentState.message}") {
                        viewModel.nextWord()// Retry fetching the word
                    }
                }
            }
        }
    }
}




