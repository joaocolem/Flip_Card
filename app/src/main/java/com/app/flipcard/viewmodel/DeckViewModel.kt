package com.app.flipcard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.flipcard.model.Deck
import com.app.flipcard.repository.DeckRepository

class DeckViewModel(private val repository: DeckRepository) : ViewModel() {

    private val _decks = MutableLiveData<List<Deck>>()
    val decks: LiveData<List<Deck>> get() = _decks

    init {
        loadDecks()
    }

    fun loadDecks() {
        _decks.value = repository.getAllDecks()
    }

    fun addDeck(deck: Deck) {
        repository.addDeck(deck)
        loadDecks()
    }

    fun deleteDeck(deckId: Long) {
        repository.deleteDeck(deckId)
        loadDecks()
    }
}