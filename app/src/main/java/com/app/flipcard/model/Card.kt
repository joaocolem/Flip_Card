package com.app.flipcard.model

data class Card(
    val id: Long = 0,
    val deckId: Long,
    val question: String,
    val answer: String,
    var isCorrect: Boolean = false
)