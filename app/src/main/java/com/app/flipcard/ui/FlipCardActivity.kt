package com.app.flipcard.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.flipcard.FlipCardApplication
import com.app.flipcard.adapters.DeckListRecycle
import com.app.flipcard.databinding.ActivityFlipCardBinding
import com.app.flipcard.model.Deck
import com.app.flipcard.viewmodel.DeckViewModel
import com.app.flipcard.viewmodel.DeckViewModelFactory

class FlipCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFlipCardBinding
    private val viewModel: DeckViewModel by viewModels {
        DeckViewModelFactory((application as FlipCardApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlipCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurando o adaptador com o clique no item
        val adapter = DeckListRecycle(emptyList()) { selectedDeck ->
            // Abrir a tela DeckDetailsActivity e passar o nome e ID do deck
            val intent = Intent(this, DeckDetailsActivity::class.java)
            intent.putExtra("DECK_ID", selectedDeck.id) // Passa o ID do deck
            intent.putExtra("DECK_NAME", selectedDeck.name) // Passa o nome do deck
            startActivity(intent)
        }

        binding.deckRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.deckRecyclerView.adapter = adapter

        // Observa mudanças na lista de decks
        viewModel.decks.observe(this) { decks ->
            adapter.updateList(decks)
        }

        // Adiciona novo deck ao clicar no botão
        binding.addDeckButton.setOnClickListener {
            val deckName = binding.newDeckEditText.text.toString()
            if (deckName.isNotEmpty()) {
                val deck = Deck(name = deckName)
                viewModel.addDeck(deck)
                binding.newDeckEditText.text.clear()
            }
        }
    }
}