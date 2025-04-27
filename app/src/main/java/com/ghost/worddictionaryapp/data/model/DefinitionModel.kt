package com.ghost.worddictionaryapp.data.model


import com.google.gson.annotations.SerializedName

data class DefinitionModel(
    val antonyms: List<Any?> = listOf(),
    val definition: String = "",
    val synonyms: List<Any?> = listOf()
)