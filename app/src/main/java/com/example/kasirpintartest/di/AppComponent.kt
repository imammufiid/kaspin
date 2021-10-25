package com.example.kasirpintartest.di

import android.content.Context
import com.example.kasirpintartest.ui.addupdate.AddUpdateActivity
import com.example.kasirpintartest.ui.checkout.CheckoutActivity
import com.example.kasirpintartest.ui.main.MainActivity
import com.example.kasirpintartest.ui.order.OrderActivity
import com.example.kasirpintartest.ui.transaction.TransactionActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: AddUpdateActivity)
    fun inject(activity: CheckoutActivity)
    fun inject(activity: MainActivity)
    fun inject(activity: OrderActivity)
    fun inject(activity: TransactionActivity)
}