package com.example.kasirpintartest.ui.addupdate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kasirpintartest.data.Repository
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.helper.DateHelper
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddUpdateViewModel @Inject constructor(private val repo: Repository) : ViewModel() {
    private var _updated = MutableLiveData<Int>()
    val updated: LiveData<Int> = _updated

    private var _inserted = MutableLiveData<Long>()
    val inserted: LiveData<Long> = _inserted

    private val _product = MutableLiveData(Product())
    val product: LiveData<Product> = _product

    val nameProduct = MutableLiveData<String>()
    val stock = MutableLiveData<String>()
    private val isEdit = MutableLiveData(false)

    fun setFieldFormParcelable(data: Product) {
        isEdit.value = true
        _product.value = data
        nameProduct.value = data.name!!
        stock.value = data.stock.toString()
    }

    fun saveProduct() {
        viewModelScope.launch {
            if (isEdit.value == true) {
                // edit
                val updatedProduct = Product(
                    id = _product.value?.id,
                    name = nameProduct.value,
                    code = _product.value?.code,
                    stock = stock.value?.toInt(),
                    date = _product.value?.date
                )
                val result = repo.updateProduct(updatedProduct)
                _product.postValue(updatedProduct)
                _updated.postValue(result.value)
            } else {
                val code = "BRG_${System.currentTimeMillis()}"
                val date = DateHelper.getCurrentDate()

                val newProduct = Product(
                    name = nameProduct.value,
                    code = code,
                    stock = stock.value?.toInt(),
                    date = date
                )

                val result = repo.insertProduct(newProduct)
                _product.postValue(newProduct)
                _inserted.postValue(result.value)
            }
        }
    }
}