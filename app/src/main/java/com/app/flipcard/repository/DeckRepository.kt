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
            arrayOf("id", "deck_id", "question", "answer"), // Colunas a serem retornadas
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
            cards.add(Card(id, deckId, question, answer))
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
        }
        return db.insert("card", null, values)
    }

    fun deleteDeck(deckId: Long) {
        val db = dbHelper.writableDatabase

        db.delete("card", "deck_id = ?", arrayOf(deckId.toString()))

        db.delete("deck", "id = ?", arrayOf(deckId.toString()))
    }
}