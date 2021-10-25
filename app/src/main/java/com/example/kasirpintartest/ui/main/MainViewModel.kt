package com.example.kasirpintartest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.data.RepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {
    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _deleted = MutableLiveData<Int>()
    val deleted: LiveData<Int> = _deleted

    private var _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    fun getProducts() {
        viewModelScope.launch {
            _loading.postValue(true)
            val result = repo.getProducts()
            _products.postValue(result.value)
            _loading.postValue(false)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            _loading.postValue(true)
            val result = repo.deleteProduct(product)
            _deleted.postValue(result.value)
            _loading.postValue(false)
        }
    }
}