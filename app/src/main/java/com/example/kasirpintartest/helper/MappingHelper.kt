package com.example.kasirpintartest.helper

import android.database.Cursor
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion.CODE
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion.DATE
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion.NAME
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion.STOCK
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion._ID
import com.example.kasirpintartest.data.entity.Product

object MappingHelper {
    fun mapCursorToArrayList(productCursor: Cursor?): ArrayList<Product> {
        val productList = ArrayList<Product>()
        productCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(_ID))
                val name = getString(getColumnIndexOrThrow(NAME))
                val code = getString(getColumnIndexOrThrow(CODE))
                val stock = getString(getColumnIndexOrThrow(STOCK)).toInt()
                val date = getString(getColumnIndexOrThrow(DATE))
                productList.add(Product(id, code, name, stock, date))
            }
        }
        return productList
    }

    fun mapCursorToProduct(productCursor: Cursor?): Product {
        var product = Product()
        productCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(_ID))
                val name = getString(getColumnIndexOrThrow(NAME))
                val code = getString(getColumnIndexOrThrow(CODE))
                val stock = getString(getColumnIndexOrThrow(STOCK)).toInt()
                val date = getString(getColumnIndexOrThrow(DATE))
                product = Product(id, code, name, stock, date)
            }
        }
        return product
    }
}