package com.app.flipcard.control

import android.content.Context
import com.app.flipcard.model.Deck
import com.app.flipcard.repository.DeckRepository

class DeckControl(var context: Context) {

    lateinit var deckRepository: DeckRepository

    init {
        deckRepository = DeckRepository(context)
    }

    fun registerDeckDatabase(deck: Deck): Long {
        return deckRepository.registerDeck(deck)
    }
}
