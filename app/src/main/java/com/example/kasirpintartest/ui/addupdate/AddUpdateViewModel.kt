package com.example.kasirpintartest.ui.addupdate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kasirpintartest.data.RepositoryImpl
import com.example.kasirpintartest.data.entity.Product
import kotlinx.coroutines.launch

class AddUpdateViewModel(private val repo: RepositoryImpl): ViewModel() {
    private var _updated = MutableLiveData<Int>()
    val updated: LiveData<Int> = _updated

    private var _inserted = MutableLiveData<Long>()
    val inserted: LiveData<Long> = _inserted

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            val result = repo.updateProduct(product)
            _updated.postValue(result.value)
        }
    }

    fun insertProduct(product: Product) {
        viewModelScope.launch {
            val result = repo.insertProduct(product)
            _inserted.postValue(result.value)
        }
    }
}