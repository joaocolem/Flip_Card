package com.app.flipcard.localdata

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseSQLite(context: Context) : SQLiteOpenHelper(
    context,
    DatabaseContract.DATABASE_NAME,
    null,
    DatabaseContract.DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DatabaseContract.DECK.SQL_CREATE)
        db?.execSQL(DatabaseContract.CARD.SQL_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {

            db?.execSQL(DatabaseContract.CARD.SQL_CREATE)
        }
        if (oldVersion < 3) {

            db?.execSQL("ALTER TABLE ${DatabaseContract.CARD.TABLE_NAME} ADD COLUMN ${DatabaseContract.CARD.COLUMN_NAME_IS_CORRECT} INTEGER DEFAULT 0;")
        }
    }
}