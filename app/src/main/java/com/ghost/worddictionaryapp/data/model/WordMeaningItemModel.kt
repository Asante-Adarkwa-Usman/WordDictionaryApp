package com.ghost.worddictionaryapp.data.model



data class WordMeaningItemModel(
    val license: LicenseModel = LicenseModel(),
    val meanings: List<MeaningModel> = listOf(),
    val phonetic: String = "",
    val phonetics: List<PhoneticModel> = listOf(),
    val sourceUrls: List<String> = listOf(),
    val word: String = ""
)