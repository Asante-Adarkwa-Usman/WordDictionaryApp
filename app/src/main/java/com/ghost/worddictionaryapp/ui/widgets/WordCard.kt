package com.ghost.worddictionaryapp.ui.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghost.worddictionaryapp.data.model.MeaningModel
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun WordCard(
    word: String,
    meanings: List<MeaningModel>,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit
) {
    val swipeThreshold = 200f // Threshold for swipe distance
    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    var cardElevation by remember { mutableStateOf(4.dp) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        cardElevation = 8.dp // Raise elevation on drag start
                    },
                    onDragEnd = {
                        if (offsetX.value.absoluteValue > swipeThreshold) {
                            if (offsetX.value > 0) {
                                onSwipeRight() // Swipe right to go to previous word
                            } else {
                                onSwipeLeft() // Swipe left to go to next word
                            }
                        }
                        // Reset position and elevation
                        coroutineScope.launch {
                            offsetX.animateTo(
                                0f,
                                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
                            )
                            cardElevation = 4.dp // Return to normal elevation
                        }
                    },
                    onDragCancel = {
                        coroutineScope.launch {
                            offsetX.animateTo(
                                0f,
                                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
                            )
                            cardElevation = 4.dp // Return to normal elevation
                        }
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume()
                        coroutineScope.launch {
                            offsetX.animateTo(
                                targetValue = offsetX.value + dragAmount,
                                animationSpec = tween(durationMillis = 1) // Very short duration for an immediate effect
                            )
                        }
                    }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = word,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            meanings.forEach { meaningItem ->
                Text(
                    text = meaningItem.partOfSpeech,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                meaningItem.definitions.forEachIndexed { index, definitionItem ->
                    Text(text = "${index + 1}. ${definitionItem.definition}")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

