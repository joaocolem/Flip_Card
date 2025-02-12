package com.app.flipcard.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.app.flipcard.localdata.DatabaseContract
import com.app.flipcard.localdata.DatabaseSQLite
import com.app.flipcard.model.Deck

class DeckRepository(context: Context) {

    lateinit var database: DatabaseSQLite

    init {
        database = DatabaseSQLite(context)
    }

    // Método para registrar um novo deck
    fun registerDeck(deck: Deck): Long {
        val db = database.writableDatabase
        val valuesDeck = ContentValues()
        valuesDeck.put(DatabaseContract.DECK.COLUMN_NAME_NAME, deck.name)

        return db.insert(DatabaseContract.DECK.TABLE_NAME, null, valuesDeck)
    }

    // Método para obter todos os decks armazenados no banco de dados
    @SuppressLint("Range")
    fun getAllDecks(): List<Deck> {
        val db = database.readableDatabase
        val cursor: Cursor = db.query(
            DatabaseContract.DECK.TABLE_NAME,
            arrayOf(DatabaseContract.DECK.COLUMN_NAME_NAME),
            null, null, null, null, null
        )

        val decks = mutableListOf<Deck>()
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex(DatabaseContract.DECK.COLUMN_NAME_NAME))
            val deck = Deck(name)
            decks.add(deck)
        }
        cursor.close()
        return decks
    }
}
