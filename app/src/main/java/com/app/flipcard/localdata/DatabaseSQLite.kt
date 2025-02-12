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
        db?.execSQL(DatabaseContract.SQL_CREATE_TABLES)

        db?.execSQL("INSERT INTO ${DatabaseContract.DECK.TABLE_NAME} (${DatabaseContract.DECK.COLUMN_NAME_NAME}) VALUES ('Matemática');")
        db?.execSQL("INSERT INTO ${DatabaseContract.DECK.TABLE_NAME} (${DatabaseContract.DECK.COLUMN_NAME_NAME}) VALUES ('Ciências');")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DatabaseContract.SQL_DROP_TABLES)
        onCreate(db)
    }
}
