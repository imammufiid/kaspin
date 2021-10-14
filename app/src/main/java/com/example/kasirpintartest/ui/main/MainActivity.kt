package com.example.kasirpintartest.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.kasirpintartest.R
import com.example.kasirpintartest.databinding.ActivityMainBinding
import com.example.kasirpintartest.ui.addupdate.AddUpdateActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var _bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_bind.root)

        _bind.btnAdd.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_add -> startActivity(Intent(this, AddUpdateActivity::class.java))
        }
    }
}