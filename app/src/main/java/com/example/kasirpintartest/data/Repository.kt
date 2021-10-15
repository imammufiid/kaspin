package com.example.kasirpintartest.data

import androidx.lifecycle.LiveData
import com.example.kasirpintartest.data.entity.Product

interface Repository {
    suspend fun getProducts(): LiveData<List<Product>>
    suspend fun deleteProduct(product: Product): LiveData<Int>
    suspend fun updateStock(product: Product, qty: Int): LiveData<Int>
    suspend fun updateProduct(product: Product): LiveData<Int>
    suspend fun insertProduct(product: Product): LiveData<Long>
}