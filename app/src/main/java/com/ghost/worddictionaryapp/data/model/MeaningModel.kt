package com.ghost.worddictionaryapp.data.model


import com.google.gson.annotations.SerializedName

data class MeaningModel(
    val antonyms: List<Any?> = listOf(),
    val definitions: List<DefinitionModel> = listOf(),
    val partOfSpeech: String = "",
    val synonyms: List<Any?> = listOf()
)