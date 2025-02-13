package com.app.flipcard.repository

import android.content.ContentValues
import android.content.Context
import com.app.flipcard.localdata.DatabaseSQLite
import com.app.flipcard.model.Card
import com.app.flipcard.model.Deck

class DeckRepository(context: Context) {

    private val dbHelper = DatabaseSQLite(context)

    // Retorna todos os decks
    fun getAllDecks(): List<Deck> {
        val decks = mutableListOf<Deck>()
        val db = dbHelper.readableDatabase
        val cursor = db.query("deck", arrayOf("id", "name"), null, null, null, null, null)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            decks.add(Deck(id, name))
        }
        cursor.close()
        return decks
    }

    // Adiciona um novo deck
    fun addDeck(deck: Deck): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", deck.name)
        }
        return db.insert("deck", null, values)
    }

    // Retorna todos os cards de um deck específico
    fun getCardsByDeck(deckId: Long): List<Card> {
        val cards = mutableListOf<Card>()
        val db = dbHelper.readableDatabase

        // Consulta para buscar apenas os cards com o deckId especificado
        val cursor = db.query(
            "card", // Nome da tabela
            arrayOf("id", "deck_id", "question", "answer", "is_correct"), // Incluímos a coluna is_correct
            "deck_id = ?", // Cláusula WHERE
            arrayOf(deckId.toString()), // Argumento para o WHERE
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val question = cursor.getString(cursor.getColumnIndexOrThrow("question"))
            val answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"))
            val isCorrect = cursor.getInt(cursor.getColumnIndexOrThrow("is_correct")) == 1 // Converte para booleano
            cards.add(Card(id, deckId, question, answer, isCorrect))
        }
        cursor.close()
        return cards
    }

    // Adiciona um novo card a um deck
    fun addCard(card: Card): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("deck_id", card.deckId)
            put("question", card.question)
            put("answer", card.answer)
            put("is_correct", if (card.isCorrect) 1 else 0) // Converte booleano para inteiro
        }
        return db.insert("card", null, values)
    }

    // Exclui um deck e seus cards associados
    fun deleteDeck(deckId: Long) {
        val db = dbHelper.writableDatabase

        db.delete("card", "deck_id = ?", arrayOf(deckId.toString()))

        db.delete("deck", "id = ?", arrayOf(deckId.toString()))
    }

    // Atualiza o status de um card (se foi respondido corretamente ou não)
    fun updateCardStatus(cardId: Long, isCorrect: Boolean) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("is_correct", if (isCorrect) 1 else 0) // Converte booleano para inteiro (1 ou 0)
        }
        db.update("card", values, "id = ?", arrayOf(cardId.toString()))
    }

    // Calcula a porcentagem de acerto de um deck
    fun getDeckAccuracy(deckId: Long): Int {
        val db = dbHelper.readableDatabase

        // Consulta para contar os cards corretos
        val correctCountQuery = """
         SELECT COUNT(*) FROM card 
         WHERE deck_id = ? AND is_correct = 1
     """
        val correctCursor = db.rawQuery(correctCountQuery, arrayOf(deckId.toString()))
        var correctCount = 0
        if (correctCursor.moveToFirst()) {
            correctCount = correctCursor.getInt(0)
        }
        correctCursor.close()

        // Consulta para contar o total de cards
        val totalCountQuery = """
         SELECT COUNT(*) FROM card 
         WHERE deck_id = ?
     """
        val totalCursor = db.rawQuery(totalCountQuery, arrayOf(deckId.toString()))
        var totalCount = 0
        if (totalCursor.moveToFirst()) {
            totalCount = totalCursor.getInt(0)
        }
        totalCursor.close()

        // Calcula a porcentagem
        return if (totalCount > 0) {
            (correctCount * 100) / totalCount
        } else {
            0 // Retorna 0% se não houver cards
        }
    }
}