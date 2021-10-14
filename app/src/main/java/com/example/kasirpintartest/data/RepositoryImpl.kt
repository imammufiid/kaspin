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
}