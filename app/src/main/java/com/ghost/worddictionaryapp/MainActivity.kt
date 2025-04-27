package com.ghost.worddictionaryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ghost.worddictionaryapp.ui.screens.WordMeaningScreen
import com.ghost.worddictionaryapp.ui.theme.WordDictionaryAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WordDictionaryAppTheme {
                   WordMeaningScreen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WordMeaningScreenPreview() {
    WordDictionaryAppTheme {
      WordMeaningScreen()
    }
}