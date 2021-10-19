package com.example.kasirpintartest.ui.addupdate

import androidx.lifecycle.*
import com.example.kasirpintartest.data.RepositoryImpl
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.helper.DateHelper
import kotlinx.coroutines.launch

class AddUpdateViewModel(private val repo: RepositoryImpl) : ViewModel() {
    private var _updated = MutableLiveData<Int>()
    val updated: LiveData<Int> = _updated

    private var _inserted = MutableLiveData<Long>()
    val inserted: LiveData<Long> = _inserted

    val nameProduct = MutableLiveData<String>()
    val stock = MutableLiveData<String>()

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            val result = repo.updateProduct(product)
            _updated.postValue(result.value)
        }
    }

    fun insertProduct() {
        viewModelScope.launch {
            val code = "BRG_${System.currentTimeMillis()}"
            val date = DateHelper.getCurrentDate()

            val newProduct = Product(
                name = nameProduct.value,
                code = code,
                stock = stock.value?.toInt(),
                date = date
            )

            val result = repo.insertProduct(newProduct)
            _inserted.postValue(result.value)
        }
    }
}