package com.example.kasirpintartest.data

import androidx.lifecycle.LiveData
import com.example.kasirpintartest.data.entity.Product

interface Repository {
    suspend fun getProducts(): LiveData<List<Product>>
}