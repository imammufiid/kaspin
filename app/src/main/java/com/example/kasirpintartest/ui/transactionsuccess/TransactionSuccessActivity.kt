package com.example.kasirpintartest.ui.transactionsuccess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kasirpintartest.databinding.ActivityTransactionSuccessBinding

class TransactionSuccessActivity : AppCompatActivity() {
    private lateinit var _bind: ActivityTransactionSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = ActivityTransactionSuccessBinding.inflate(layoutInflater)
        setContentView(_bind.root)

        _bind.btnHome.setOnClickListener {

        }
    }
}