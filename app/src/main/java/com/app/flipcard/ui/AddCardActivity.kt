package com.app.flipcard.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.flipcard.databinding.ActivityAddCardBinding
import com.app.flipcard.model.Card
import com.app.flipcard.repository.DeckRepository

class AddCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCardBinding
    private lateinit var repository: DeckRepository
    private var deckId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = DeckRepository(applicationContext)


        deckId = intent.getLongExtra("DECK_ID", -1)
        if (deckId == -1L) {
            Toast.makeText(this, "Erro ao associar o card ao deck.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }


        binding.saveCardButton.setOnClickListener {
            val question = binding.questionEditText.text.toString()
            val answer = binding.answerEditText.text.toString()

            if (question.isNotEmpty() && answer.isNotEmpty()) {

                val card = Card(deckId = deckId, question = question, answer = answer)
                repository.addCard(card)

                Toast.makeText(this, "Card adicionado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}