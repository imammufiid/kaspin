package com.example.kasirpintartest.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion.TABLE_NAME
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion._ID
import java.sql.SQLException

class ProductHelper(context: Context) {
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME

        /**
         * This method for create instance ProductHelper class
         */
        private var instance: ProductHelper? = null
        fun getInstance(context: Context): ProductHelper =
            instance ?: synchronized(this) {
                instance ?: ProductHelper(context)
            }
    }

    /**
     * open method is useful for opening database
     */
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    /**
     * close method is useful for closing database
     */
    fun close() {
        dataBaseHelper.close()
        if (database.isOpen) {
            dataBaseHelper.close()
        }
    }

    /**
     * get all products from db
     */
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE, null, null, null, null, null, "$_ID ASC"
        )
    }

    /**
     * query get product by ID
     */
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE, null, "$_ID = ?", arrayOf(id), null, null, null, null
        )
    }

    /**
     * query save product
     */
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    /**
     * query update product
     */
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }
}