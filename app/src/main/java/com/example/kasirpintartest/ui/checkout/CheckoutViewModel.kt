package com.example.kasirpintartest.ui.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kasirpintartest.data.Repository
import com.example.kasirpintartest.data.entity.Product
import kotlinx.coroutines.launch
import javax.inject.Inject

class CheckoutViewModel @Inject constructor(private val repo: Repository) : ViewModel() {
    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _updated = MutableLiveData<Int>()
    val updated: LiveData<Int> = _updated

    fun updateStock(product: Product, qty: Int) {
        viewModelScope.launch {
            _loading.postValue(true)
            val result = repo.updateStock(product, qty)
            _updated.postValue(result.value)
            _loading.postValue(false)
        }
    }
}