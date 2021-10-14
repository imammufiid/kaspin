package com.example.kasirpintartest.helper

import android.content.Context
import com.example.kasirpintartest.data.LocalDataSource
import com.example.kasirpintartest.data.db.ProductHelper
import com.example.kasirpintartest.data.RepositoryImpl

object Injection {
    fun provideRepository(context: Context): RepositoryImpl {
        val productHelper = ProductHelper.getInstance(context)
        val localDataSource = LocalDataSource.getInstance(productHelper)

        return RepositoryImpl.getInstance(localDataSource)
    }
}