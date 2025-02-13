package com.app.flipcard.model

data class Card(
    val id: Long = 0,       // ID único do card
    val deckId: Long,       // ID do deck ao qual este card pertence
    val question: String,   // Pergunta (frente do card)
    val answer: String      // Resposta (verso do card)
)
