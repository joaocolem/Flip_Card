package com.app.flipcard.model

data class Deck(
    val id: Long = 0, // ID único (gerado automaticamente pelo banco de dados)
    val name: String
)