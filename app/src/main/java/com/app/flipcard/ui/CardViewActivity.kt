package com.app.flipcard.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
    private var currentIndex = 0
    private var isShowingAnswer = false // Indica se está mostrando a pergunta ou a resposta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = DeckRepository(applicationContext)

        val deckId = intent.getLongExtra("DECK_ID", -1)
        if (deckId == -1L) {
            Toast.makeText(this, "Erro ao carregar os cards.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        cards = repository.getCardsByDeck(deckId)

        if (cards.isEmpty()) {
            Toast.makeText(this, "Nenhum card encontrado neste deck.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        showCard()

        // Aplica flip ao tocar no botão "Mostrar Resposta"
        binding.showAnswerButton.setOnClickListener {
            flipCard()
        }

        binding.correctButton.setOnClickListener {
            markAnswer(true)
        }

        binding.wrongButton.setOnClickListener {
            markAnswer(false)
        }
    }

    private fun showCard() {
        if (currentIndex < cards.size) {
            val card = cards[currentIndex]
            binding.cardTextView.text = card.question // Começa mostrando a pergunta
            isShowingAnswer = false

            binding.showAnswerButton.isEnabled = true
            binding.correctButton.isEnabled = false
            binding.wrongButton.isEnabled = false
        }
    }

    private fun flipCard() {
        if (currentIndex >= cards.size) return

        val flipOut = ObjectAnimator.ofFloat(binding.cardContainer, "rotationY", 0f, 90f).apply {
            duration = 300
        }

        val flipIn = ObjectAnimator.ofFloat(binding.cardContainer, "rotationY", -90f, 0f).apply {
            duration = 300
        }

        flipOut.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                val card = cards[currentIndex]
                binding.cardTextView.text = if (isShowingAnswer) card.question else card.answer
                isShowingAnswer = !isShowingAnswer

                // Ajusta os botões corretamente
                binding.showAnswerButton.isEnabled = !isShowingAnswer
                binding.correctButton.isEnabled = isShowingAnswer
                binding.wrongButton.isEnabled = isShowingAnswer

                AnimatorSet().apply {
                    play(flipIn)
                    start()
                }
            }

            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

        AnimatorSet().apply {
            play(flipOut)
            start()
        }
    }

    private fun markAnswer(isCorrect: Boolean) {
        if (currentIndex < cards.size) {
            val card = cards[currentIndex]
            repository.updateCardStatus(card.id, isCorrect)
            nextCard()
        }
    }

    private fun nextCard() {
        currentIndex++
        if (currentIndex < cards.size) {
            showCard()
        } else {
            Toast.makeText(this, "Você chegou ao final do deck!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
