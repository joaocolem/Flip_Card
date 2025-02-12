package com.app.flipcard.adapters

import android.content.Context
import android.widget.ArrayAdapter
import com.app.flipcard.model.Deck

class DeckListAdapter(context: Context) {
    lateinit var itemAdapter: ArrayAdapter<String>
    var decks: MutableList<String> = mutableListOf()

    init {
        itemAdapter = ArrayAdapter(
            context,
            android.R.layout.simple_list_item_1,
            decks
        )
    }

    fun addNewDeck(deck: Deck) {
        decks.add(deck.name)
        itemAdapter.notifyDataSetChanged()
    }

    fun removeDeck(pos: Int) {
        if (pos < decks.size) {
            decks.removeAt(pos)
            itemAdapter.notifyDataSetChanged()
        }
    }
}
