package com.example.kasirpintartest.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.kasirpintartest.R
import com.example.kasirpintartest.databinding.ActivityHomeBinding
import com.example.kasirpintartest.ui.main.MainActivity
import com.example.kasirpintartest.ui.transaction.TransactionActivity

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var _bind: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(_bind.root)

        _bind.btnProduct.setOnClickListener(this)
        _bind.btnTransaction.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_product -> startActivity(Intent(this, MainActivity::class.java))
            R.id.btn_transaction -> startActivity(Intent(this, TransactionActivity::class.java))
        }
    }
}