package com.example.kasirpintartest.data.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class ProductColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "product"
            const val _ID = "_id"
            const val CODE = "code"
            const val NAME = "name"
            const val STOCK = "stock"
        }
    }
}