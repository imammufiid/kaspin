package com.example.kasirpintartest.data

import android.content.ContentValues
import com.example.kasirpintartest.data.db.DatabaseContract
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

    fun deleteProduct(product: Product): Int {
        productHelper.open()
         val result = productHelper.deleteById(product.id.toString()).toLong()
        productHelper.close()
        return result.toInt()
    }

    fun updateStock(product: Product, qty: Int): Int {
        val values = ContentValues().apply {
            put(DatabaseContract.ProductColumns.STOCK, product.stock?.minus(qty))
        }
        productHelper.open()
        val result = productHelper.update(product.id.toString(), values)
        productHelper.close()
        return result
    }

    fun updateProduct(product: Product): Int {
        val values = ContentValues().apply {
            put(DatabaseContract.ProductColumns.NAME, product.name)
            put(DatabaseContract.ProductColumns.STOCK, product.stock)
        }
        productHelper.open()
        val result = productHelper.update(product.id.toString(), values)
        productHelper.close()
        return result
    }

    fun insertProduct(product: Product): Long {
        val values = ContentValues().apply {
            put(DatabaseContract.ProductColumns.NAME, product.name)
            put(DatabaseContract.ProductColumns.CODE, product.code)
            put(DatabaseContract.ProductColumns.STOCK, product.stock)
            put(DatabaseContract.ProductColumns.DATE, product.date)
        }
        productHelper.open()
        val result = productHelper.insert(values)
        productHelper.close()
        return result
    }
}