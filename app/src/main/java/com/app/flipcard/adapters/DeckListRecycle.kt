package com.app.flipcard.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.flipcard.R
import com.app.flipcard.model.Deck
import com.app.flipcard.ui.CardViewerActivity

class DeckListRecycle(
    private var decks: List<Deck>,
    private val onDeckClick: (Deck) -> Unit // Callback para cliques
) : RecyclerView.Adapter<DeckListRecycle.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.deckNameTextView)
        val editBtn: ImageButton = itemView.findViewById(R.id.editIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.deck_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deck = decks[position]
        holder.nameText.text = deck.name

        // Clique no botão editIcon
        holder.editBtn.setOnClickListener {
            onDeckClick(deck) // Chama o callback passando o deck clicado
        }

        // Clique no container do item
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CardViewerActivity::class.java)
            intent.putExtra("DECK_ID", deck.id) // Passa o ID do deck para a próxima Activity
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = decks.size

    fun updateList(newDecks: List<Deck>) {
        decks = newDecks
        notifyDataSetChanged()
    }
}