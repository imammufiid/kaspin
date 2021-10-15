package com.example.kasirpintartest.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasirpintartest.R
import com.example.kasirpintartest.data.entity.ProductCheckout
import com.example.kasirpintartest.databinding.ActivityCheckoutBinding
import com.example.kasirpintartest.ui.transactionsuccess.TransactionSuccessActivity
import com.example.kasirpintartest.viewmodel.ViewModelFactory

class CheckoutActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var _bind: ActivityCheckoutBinding
    private var products: List<ProductCheckout>? = null
    private lateinit var checkoutAdapter: CheckoutAdapter

    companion object {
        const val PRODUCTS_EXTRAS = "products_extras"
    }

    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory.getInstance(this))[CheckoutViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(_bind.root)
        supportActionBar?.title = getString(R.string.checkout_title)

        _bind.btnCancel.setOnClickListener(this)
        _bind.btnSubmit.setOnClickListener(this)
        _bind.btnSave.setOnClickListener(this)

        products = intent.getParcelableArrayListExtra(PRODUCTS_EXTRAS)
        initRV()
        observeVM()
    }

    private fun initRV() {

        checkoutAdapter = CheckoutAdapter { product, position ->
            if (product.qty!! > 1) {
                product.qty = product.qty!!.minus(1)
                checkoutAdapter.updateItem(position, product)
            } else {
                checkoutAdapter.removeItem(position)
            }
        }

        checkoutAdapter.addData(products)

        _bind.rvProducts.apply {
            layoutManager = LinearLayoutManager(this@CheckoutActivity)
            setHasFixedSize(true)
            adapter = checkoutAdapter
        }
    }

    private fun observeVM() {
        viewModel.loading.observe(this, {
            if (it) {
                _bind.progressbar.visibility = View.VISIBLE
            } else {
                _bind.progressbar.visibility = View.GONE
            }
        })
        viewModel.updated.observe(this, { result ->
            if (result <= 0) {
                Toast.makeText(this, getString(R.string.failed_transaction), Toast.LENGTH_SHORT).show()
                return@observe
            }
            startActivity(Intent(this, TransactionSuccessActivity::class.java))
            finish()

        })
    }

    override fun onBackPressed() {
        val dialogMessage = getString(R.string.message_delete_product)
        val dialogTitle = getString(R.string.title_cancle_transaction)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                finish()
            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_cancel -> {
                val dialogMessage = getString(R.string.message_delete_product)
                val dialogTitle = getString(R.string.title_cancle_transaction)

                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle(dialogTitle)
                alertDialogBuilder
                    .setMessage(dialogMessage)
                    .setCancelable(false)
                    .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        finish()
                    }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
            R.id.btn_submit -> {
                products?.forEach {
                    it.product?.let { product ->
                        it.qty?.let { qty ->
                            viewModel.updateStock(
                                product,
                                qty
                            )
                        }
                    }
                }
            }
        }
    }
}