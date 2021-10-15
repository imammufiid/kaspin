package com.example.kasirpintartest.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kasirpintartest.data.entity.Product

class RepositoryImpl(private val local: LocalDataSource) : Repository {
    companion object {
        @Volatile
        private var instance: RepositoryImpl? = null
        fun getInstance(local: LocalDataSource): RepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: RepositoryImpl(local)
            }
    }

    override suspend fun getProducts(): LiveData<List<Product>> {
        val result = MutableLiveData<List<Product>>()
        val local = local.getProducts()
        result.value = local
        return result
    }

    override suspend fun deleteProduct(product: Product): LiveData<Int> {
        val result = MutableLiveData<Int>()
        val localResult = local.deleteProduct(product)
        result.value = localResult
        return result
    }

    override suspend fun updateStock(product: Product, qty: Int): LiveData<Int> {
        val result = MutableLiveData<Int>()
        val productID = local.productByID(product.id.toString())
        val localResult = local.updateStock(productID, qty)
        result.value = localResult
        return result
    }

    override suspend fun updateProduct(product: Product): LiveData<Int> {
        val result = MutableLiveData<Int>()
        val localResult = local.updateProduct(product)
        result.value = localResult
        return result
    }

    override suspend fun insertProduct(product: Product): LiveData<Long> {
        val result = MutableLiveData<Long>()
        val localResult = local.insertProduct(product)
        result.value = localResult
        return result
    }
}