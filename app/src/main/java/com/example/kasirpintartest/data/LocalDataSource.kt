package com.example.kasirpintartest.data

import com.example.kasirpintartest.data.db.ProductHelper
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.helper.MappingHelper

class LocalDataSource(private val productHelper: ProductHelper) {
    companion object {
        @Volatile
        private var instance: LocalDataSource? = null
        fun getInstance(productHelper: ProductHelper): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(productHelper)
            }
    }

    fun getProducts(): ArrayList<Product> {
        var result = ArrayList<Product>()
        productHelper.open()
        val cursor = productHelper.queryAll()
        result = MappingHelper.mapCursorToArrayList(cursor)
        productHelper.close()
        return result
    }
}