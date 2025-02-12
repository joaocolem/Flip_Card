package com.app.flipcard.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DeckLiveData : ViewModel() {

    private val _decks = MutableLiveData<MutableList<Deck>>(mutableListOf())
    val decks: LiveData<MutableList<Deck>> get() = _decks

    fun addDeck(deck: Deck) {
        _decks.value = _decks.value?.apply { add(deck) }
        _decks.postValue(_decks.value)
    }
}
