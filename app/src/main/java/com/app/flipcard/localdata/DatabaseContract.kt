package com.app.flipcard.localdata

class DatabaseContract {

    companion object {
        const val DATABASE_NAME: String = "flipcard.db"
        const val DATABASE_VERSION = 3

        const val SQL_CREATE_TABLES = DECK.SQL_CREATE + CARD.SQL_CREATE
        const val SQL_DROP_TABLES = DECK.SQL_DROP + CARD.SQL_DROP
    }

    object DECK {
        const val TABLE_NAME = "deck"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NAME = "name"

        const val SQL_CREATE = """
    CREATE TABLE IF NOT EXISTS $TABLE_NAME (
        $COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_NAME_NAME TEXT NOT NULL
    );
    """

        const val SQL_DROP = "DROP TABLE IF EXISTS $TABLE_NAME;"
    }

    object CARD {
        const val TABLE_NAME = "card"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_DECK_ID = "deck_id"
        const val COLUMN_NAME_QUESTION = "question"
        const val COLUMN_NAME_ANSWER = "answer"
        const val COLUMN_NAME_IS_CORRECT = "is_correct"

        const val SQL_CREATE = """
    CREATE TABLE IF NOT EXISTS $TABLE_NAME (
        $COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_NAME_DECK_ID INTEGER NOT NULL,
        $COLUMN_NAME_QUESTION TEXT NOT NULL,
        $COLUMN_NAME_ANSWER TEXT NOT NULL,
        $COLUMN_NAME_IS_CORRECT INTEGER DEFAULT 0, -- Nova coluna adicionada
        FOREIGN KEY($COLUMN_NAME_DECK_ID) REFERENCES ${DECK.TABLE_NAME}(${DECK.COLUMN_NAME_ID})
    );
    """

        const val SQL_DROP = "DROP TABLE IF EXISTS $TABLE_NAME;"
    }
}