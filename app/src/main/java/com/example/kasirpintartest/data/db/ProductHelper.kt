package com.example.kasirpintartest.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion.TABLE_NAME

class ProductHelper(context: Context) {
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
    }
}