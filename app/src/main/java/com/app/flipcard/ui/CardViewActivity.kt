package com.app.flipcard.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.flipcard.databinding.ActivityCardViewerBinding
import com.app.flipcard.model.Card
import com.app.flipcard.repository.DeckRepository

class CardViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardViewerBinding
    private lateinit var repository: DeckRepository
    private var cards: List<Card> = emptyList()
    private var currentIndex = 0 // Índice do card atual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = DeckRepository(applicationContext)

        // Recupera o ID do deck enviado pela FlipCardActivity
        val deckId = intent.getLongExtra("DECK_ID", -1)
        if (deckId == -1L) {
            Toast.makeText(this, "Erro ao carregar os cards.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Carrega os cards do deck
        cards = repository.getCardsByDeck(deckId)

        if (cards.isEmpty()) {
            Toast.makeText(this, "Nenhum card encontrado neste deck.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Exibe o primeiro card
        showCard()

        // Configura o clique no botão "Mostrar Resposta"
        binding.showAnswerButton.setOnClickListener {
            showAnswer()
        }

        // Configura o clique no botão "Acertou"
        binding.correctButton.setOnClickListener {
            markAnswer(true) // Marca como correto
        }

        // Configura o clique no botão "Errou"
        binding.wrongButton.setOnClickListener {
            markAnswer(false) // Marca como incorreto
        }
    }

    private fun showCard() {
        if (currentIndex < cards.size) {
            val card = cards[currentIndex]
            binding.cardTextView.text = card.question // Mostra a pergunta
            binding.showAnswerButton.isEnabled = true // Habilita o botão "Mostrar Resposta"
            binding.correctButton.isEnabled = false // Desabilita os botões de acerto/erro
            binding.wrongButton.isEnabled = false
        }
    }

    private fun showAnswer() {
        if (currentIndex < cards.size) {
            val card = cards[currentIndex]
            binding.cardTextView.text = card.answer // Mostra a resposta
            binding.showAnswerButton.isEnabled = false // Desabilita o botão "Mostrar Resposta"
            binding.correctButton.isEnabled = true // Habilita os botões de acerto/erro
            binding.wrongButton.isEnabled = true
        }
    }

    private fun markAnswer(isCorrect: Boolean) {
        if (currentIndex < cards.size) {
            val card = cards[currentIndex]
            repository.updateCardStatus(card.id, isCorrect) // Atualiza o status no banco
            nextCard() // Passa para o próximo card
        }
    }

    private fun nextCard() {
        currentIndex++
        if (currentIndex < cards.size) {
            showCard() // Mostra o próximo card
        } else {
            Toast.makeText(this, "Você chegou ao final do deck!", Toast.LENGTH_SHORT).show()
            finish() // Fecha a Activity quando todos os cards forem exibidos
        }
    }
}