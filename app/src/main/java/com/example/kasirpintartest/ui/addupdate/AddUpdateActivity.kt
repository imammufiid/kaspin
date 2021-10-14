package com.example.kasirpintartest.ui.addupdate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kasirpintartest.R
import com.example.kasirpintartest.data.db.ProductHelper
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.databinding.ActivityAddUpdateBinding

class AddUpdateActivity : AppCompatActivity() {
    private lateinit var _bind: ActivityAddUpdateBinding

    private var isEdit = false
    private var product: Product? = null
    private var position: Int = 0
    private lateinit var productHelper: ProductHelper

    companion object {
        const val EXTRA_PRODUCT = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = ActivityAddUpdateBinding.inflate(layoutInflater)
        setContentView(_bind.root)

        productHelper = ProductHelper.getInstance(applicationContext)
        productHelper.open()

        product = intent.getParcelableExtra(EXTRA_PRODUCT)
        isEditProduct()
        setupActionBar()
    }

    private fun setupActionBar() {
        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.edit)
            btnTitle = getString(R.string.update)

            product?.let {
                _bind.edtName.setText(it.name)
                _bind.edtStock.setText(it.stock.toString())
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        _bind.btnSubmit.text = btnTitle
    }

    private fun isEditProduct() {
        if (product != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            product = Product()
        }
    }
}