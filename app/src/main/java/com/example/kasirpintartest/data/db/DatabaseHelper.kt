package com.example.kasirpintartest.data.db

import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion.CODE
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion.DATE
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion.NAME
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion.STOCK
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion.TABLE_NAME
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion._ID

internal class DatabaseHelper {
    companion object {
        private const val DATABASE_NAME = "dbkasirpntar"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_PRODUCT = "CREATE TABLE $TABLE_NAME" +
                " (${_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $CODE VARCHAR(50) NOT NULL UNIQUE," +
                " $NAME VARCHAR(255)" +
                " $STOCK INTEGER NOT NULL" +
                " $DATE TEXT NOT NULL),"
    }
}