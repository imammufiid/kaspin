package com.example.kasirpintartest.ui.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kasirpintartest.data.RepositoryImpl
import com.example.kasirpintartest.data.entity.Order
import com.example.kasirpintartest.vo.Resource
import com.example.kasirpintartest.vo.Status
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(private val repo: RepositoryImpl) : ViewModel() {
    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private var _order = MutableLiveData<List<Order>>()
    val order: LiveData<List<Order>> = _order

    fun getOrder() {
        viewModelScope.launch {
            _loading.postValue(true)
            repo.orders {
                when (it.status) {
                    Status.SUCCESS -> _order.postValue(it.data!!)
                    Status.ERROR -> _message.postValue(it.message!!)
                    else -> _message.postValue("asdfadsf")
                }
                _loading.postValue(false)
            }
        }
    }

}