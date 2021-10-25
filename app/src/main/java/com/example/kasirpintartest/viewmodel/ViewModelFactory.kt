package com.example.kasirpintartest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kasirpintartest.data.Repository
import com.example.kasirpintartest.ui.addupdate.AddUpdateViewModel
import com.example.kasirpintartest.ui.checkout.CheckoutViewModel
import com.example.kasirpintartest.ui.main.MainViewModel
import com.example.kasirpintartest.ui.order.OrderViewModel
import com.example.kasirpintartest.ui.transaction.TransactionViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repo: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repo) as T
            modelClass.isAssignableFrom(TransactionViewModel::class.java) -> TransactionViewModel(
                repo
            ) as T
            modelClass.isAssignableFrom(CheckoutViewModel::class.java) -> CheckoutViewModel(repo) as T
            modelClass.isAssignableFrom(AddUpdateViewModel::class.java) -> AddUpdateViewModel(repo) as T
            modelClass.isAssignableFrom(OrderViewModel::class.java) -> OrderViewModel(repo) as T
            else -> throw Throwable("Unknown ViewModel Class: ${modelClass.name}")
        }
    }
}