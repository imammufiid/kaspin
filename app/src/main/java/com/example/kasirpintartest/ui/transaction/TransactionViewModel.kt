package com.example.kasirpintartest.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kasirpintartest.data.Repository
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.data.entity.ProductCheckout
import kotlinx.coroutines.launch
import javax.inject.Inject

class TransactionViewModel @Inject constructor(private val repo: Repository) : ViewModel() {
    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private var _checkout = MutableLiveData<List<ProductCheckout>>()
    val checkout: LiveData<List<ProductCheckout>> = _checkout

    private var productCheckout: MutableList<ProductCheckout> = mutableListOf()

    fun getProducts() {
        viewModelScope.launch {
            _loading.postValue(true)
            val result = repo.getProducts()
            _products.postValue(result.value)
            _loading.postValue(false)
        }
    }

    fun setProductCheckout(product: Product) {
        val productId = product.id

        val isContains = productCheckout.filter { it.product?.id == productId }
        if (isContains.isNotEmpty()) {
            val itemProduct = productCheckout.find { it.product?.id == productId }
            itemProduct?.qty = itemProduct?.qty?.plus(1)
        } else {
            productCheckout.add(ProductCheckout(product, 1))
        }
        _checkout.value = productCheckout
    }
}