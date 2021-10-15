package com.example.kasirpintartest.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kasirpintartest.data.RepositoryImpl
import com.example.kasirpintartest.helper.Injection
import com.example.kasirpintartest.ui.addupdate.AddUpdateActivity
import com.example.kasirpintartest.ui.addupdate.AddUpdateViewModel
import com.example.kasirpintartest.ui.checkout.CheckoutViewModel
import com.example.kasirpintartest.ui.main.MainViewModel
import com.example.kasirpintartest.ui.transaction.TransactionViewModel

class ViewModelFactory private constructor(private val repo: RepositoryImpl) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repo) as T
            modelClass.isAssignableFrom(TransactionViewModel::class.java) -> TransactionViewModel(repo) as T
            modelClass.isAssignableFrom(CheckoutViewModel::class.java) -> CheckoutViewModel(repo) as T
            modelClass.isAssignableFrom(AddUpdateViewModel::class.java) -> AddUpdateViewModel(repo) as T
            else -> throw Throwable("Unknown ViewModel Class: ${modelClass.name}")
        }
    }
}