package com.app.flipcard.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.flipcard.R
import com.app.flipcard.model.Deck

class DeckListCustomItem(context: Context): BaseAdapter() {

    var decks: MutableList<Deck> = mutableListOf()
    lateinit var inflater: LayoutInflater

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return decks.size
    }

    override fun getItem(position: Int): Any {
        return decks[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var tempView = convertView

        if (tempView == null) {
            tempView = inflater.inflate(R.layout.deck_item, parent, false)

            tempView.findViewById<TextView>(R.id.deckNameTextView).text = decks[position].name

            tempView.findViewById<ImageButton>(R.id.editIcon).setOnClickListener {
                println("Edit Deck: ${decks[position].name}")
                tempView.findViewById<ConstraintLayout>(R.id.deck_item_view).setBackgroundColor(Color.BLUE)
            }
        }

        return tempView!!
    }

    fun addDeck(deck: Deck) {
        this.decks.add(deck)
        this.notifyDataSetChanged()
    }
}
