package com.app.flipcard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.flipcard.repository.DeckRepository

class DeckViewModelFactory(
    private val repository: DeckRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DeckViewModel::class.java)) {
            return DeckViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}