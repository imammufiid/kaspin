package com.example.kasirpintartest.ui.transactionsuccess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.kasirpintartest.R
import com.example.kasirpintartest.databinding.ActivityTransactionSuccessBinding

class TransactionSuccessActivity : AppCompatActivity() {
    private lateinit var _bind: ActivityTransactionSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = DataBindingUtil.setContentView(this, R.layout.activity_transaction_success)

        _bind.btnHome.setOnClickListener {
            finish()
        }
    }
}