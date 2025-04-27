package com.ghost.worddictionaryapp.data.model



data class DefinitionModel(
    val antonyms: List<Any?> = listOf(),
    val definition: String = "",
    val synonyms: List<Any?> = listOf()
)