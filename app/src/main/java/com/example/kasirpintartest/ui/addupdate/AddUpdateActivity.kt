package com.example.kasirpintartest.ui.addupdate

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kasirpintartest.R
import com.example.kasirpintartest.data.db.DatabaseContract
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion.CODE
import com.example.kasirpintartest.data.db.DatabaseContract.ProductColumns.Companion.DATE
import com.example.kasirpintartest.data.db.ProductHelper
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.databinding.ActivityAddUpdateBinding
import java.text.SimpleDateFormat
import java.util.*

class AddUpdateActivity : AppCompatActivity(), View.OnClickListener {
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

        _bind.btnSubmit.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_submit) {
            val name = _bind.edtName.text.toString().trim()
            val stock = _bind.edtStock.text.toString().trim()
            if (title.isEmpty()) {
                _bind.edtName.error = "Field can not be blank"
                return
            }
            product?.name = name
            product?.stock = stock.toInt()
            val intent = Intent().apply {
                putExtra(EXTRA_PRODUCT, product)
                putExtra(EXTRA_POSITION, position)
            }

            val values = ContentValues().apply {
                put(DatabaseContract.ProductColumns.NAME, name)
                put(DatabaseContract.ProductColumns.STOCK, stock)
            }

            if (isEdit) {
                val result = productHelper.update(product?.id.toString(), values).toLong()
                if (result > 0) {
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    Toast.makeText(this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
                }
            } else {
                product?.date = getCurrentDate()
                values.put(DATE, getCurrentDate())
                values.put(CODE, "BRG_${System.currentTimeMillis()}")
                val result = productHelper.insert(values)
                if (result > 0) {
                    product?.id = result.toInt()
                    setResult(RESULT_ADD, intent)
                    finish()
                } else {
                    Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}