package com.app.flipcard.localdata

class DatabaseContract {

    companion object {
        const val DATABASE_NAME: String = "flipcard.db"
        const val DATABASE_VERSION = 1

        const val SQL_CREATE_TABLES = DECK.SQL_CREATE
        const val SQL_DROP_TABLES = DECK.SQL_DROP
    }

    object DECK {
        const val TABLE_NAME = "deck"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NAME = "name"

        const val SQL_CREATE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME_NAME TEXT NOT NULL);"

        const val SQL_DROP = "DROP TABLE IF EXISTS $TABLE_NAME;"
    }
}