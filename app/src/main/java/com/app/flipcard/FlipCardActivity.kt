package com.app.flipcard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.flipcard.databinding.ActivityFlipCardBinding
import com.app.flipcard.model.Deck
import com.app.flipcard.repository.DeckRepository

class FlipCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFlipCardBinding
    private lateinit var deckRepository: DeckRepository

    private val deckList = mutableListOf<Deck>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFlipCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar a interface para ajustar ao sistema de barras
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa o repositório
        deckRepository = DeckRepository(this)

        // Carregar decks do banco de dados
        loadDecks()

        // Configura os eventos dos botões
        registerEvents()
    }

    private fun loadDecks() {
        // Carrega decks salvos no banco
        val dbDecks = deckRepository.getAllDecks()
        deckList.clear()
        deckList.addAll(dbDecks)
        val deckListAdapter = DeckListRecycle(deckList)
        binding.deckRecyclerView.adapter = deckListAdapter
    }

    private fun registerEvents() {
        // Evento do botão de adicionar novo deck
        binding.addDeckButton.setOnClickListener {
            val deckName = binding.newDeckEditText.text.toString()
            if (deckName.isNotEmpty()) {
                val newDeck = Deck(deckName)
                val id = deckRepository.registerDeck(newDeck) // Registra o deck no banco
                newDeck.id = id // Atribui o id ao objeto
                deckList.add(newDeck)
                binding.deckRecyclerView.adapter?.notifyItemInserted(deckList.size - 1)
                binding.newDeckEditText.text.clear()
                Toast.makeText(this, "Baralho '$deckName' adicionado!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, insira o nome do baralho", Toast.LENGTH_SHORT).show()
            }
        }

        // Evento de navegação para outra tela ou ação ao selecionar um deck
        binding.deckRecyclerView.setOnClickListener {
            val intent = Intent(this, DeckDetailActivity::class.java)
            startActivity(intent)
        }

        // Evento de sair
        binding.exitButton.setOnClickListener {
            finish()
        }
    }
}
