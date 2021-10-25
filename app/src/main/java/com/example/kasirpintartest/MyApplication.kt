package com.example.kasirpintartest

import android.app.Application
import com.example.kasirpintartest.di.AppComponent
import com.example.kasirpintartest.di.DaggerAppComponent

class MyApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}