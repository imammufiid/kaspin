package com.example.kasirpintartest.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kasirpintartest.data.Repository
import com.example.kasirpintartest.data.entity.Order
import com.example.kasirpintartest.vo.Status
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderViewModel @Inject constructor(private val repo: Repository) : ViewModel() {
    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private var _order = MutableLiveData<List<Order>>()
    val order: LiveData<List<Order>> = _order

    fun getOrder() {
        viewModelScope.launch {
            _loading.postValue(true)
            repo.getOrders {
                when (it.status) {
                    Status.SUCCESS -> _order.postValue(it.data!!)
                    Status.ERROR -> _message.postValue(it.message!!)
                    else -> _message.postValue("asdfadsf")
                }
                _loading.postValue(false)
            }
        }
    }

    fun removeOrder(id: String) {
        viewModelScope.launch {
            repo.removeOrder(id)
        }
    }

}