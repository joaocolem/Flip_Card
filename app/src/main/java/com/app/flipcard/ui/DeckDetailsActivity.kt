package com.app.flipcard.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.flipcard.FlipCardApplication
import com.app.flipcard.adapters.CardListRecycle
import com.app.flipcard.databinding.DeckDetailsBinding
import com.app.flipcard.repository.DeckRepository
import com.app.flipcard.viewmodel.DeckViewModel
import com.app.flipcard.viewmodel.DeckViewModelFactory

class DeckDetailsActivity : AppCompatActivity() {

    private lateinit var binding: DeckDetailsBinding
    private lateinit var repository: DeckRepository
    private var deckId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DeckDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = DeckRepository(applicationContext)

        // Recupera o ID e o nome do deck enviados pela FlipCardActivity
        deckId = intent.getLongExtra("DECK_ID", -1)
        val deckName = intent.getStringExtra("DECK_NAME") ?: "Sem Nome"

        if (deckId == -1L) {
            Toast.makeText(this, "Erro ao carregar o deck.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Exibe o nome do deck no topo da tela
        binding.deckNameTextView.text = deckName

        // Carrega os cards associados ao deck
        loadCards()

        // Configura o clique no botão de adicionar card
        binding.addCardButton.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            intent.putExtra("DECK_ID", deckId) // Passa o ID do deck para a próxima Activity
            startActivity(intent)
        }

        // Configura o clique no botão de excluir deck
        binding.deleteDeckButton.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    override fun onResume() {
        super.onResume()
        // Recarrega os cards quando voltar da AddCardActivity
        loadCards()
    }

    private fun loadCards() {
        val cards = repository.getCardsByDeck(deckId)
        val adapter = CardListRecycle(cards)
        binding.cardRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cardRecyclerView.adapter = adapter
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Excluir Deck")
            .setMessage("Tem certeza de que deseja excluir este deck? Todos os cards associados serão removidos.")
            .setPositiveButton("Sim") { _, _ ->
                deleteDeck()
            }
            .setNegativeButton("Não", null)
            .show()
    }

    private fun deleteDeck() {
        val viewModel: DeckViewModel by viewModels {
            DeckViewModelFactory((application as FlipCardApplication).repository)
        }

        viewModel.deleteDeck(deckId) // Notifica o ViewModel para deletar o deck
        Toast.makeText(this, "Deck excluído com sucesso!", Toast.LENGTH_SHORT).show()
        finish() // Fecha a Activity e volta para a FlipCardActivity
    }
}