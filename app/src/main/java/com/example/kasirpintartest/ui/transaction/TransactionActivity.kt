package com.example.kasirpintartest.ui.transaction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasirpintartest.MyApplication
import com.example.kasirpintartest.R
import com.example.kasirpintartest.data.entity.ProductCheckout
import com.example.kasirpintartest.databinding.ActivityTransactionBinding
import com.example.kasirpintartest.ui.checkout.CheckoutActivity
import com.example.kasirpintartest.ui.order.OrderActivity
import com.example.kasirpintartest.viewmodel.ViewModelFactory
import javax.inject.Inject

class TransactionActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var _bind: ActivityTransactionBinding
    private lateinit var transactionAdapter: TransactionAdapter
    private var productCheckout: List<ProductCheckout> = listOf()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[TransactionViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _bind = DataBindingUtil.setContentView(this, R.layout.activity_transaction)
        _bind.viewmodel = viewModel
        _bind.lifecycleOwner = this

        supportActionBar?.title = getString(R.string.transaction)
        _bind.btnCheckout.setOnClickListener(this)

        viewModel.getProducts()
        initRV()
        observeVM()
    }

    private fun observeVM() {
        viewModel.products.observe(this, { product ->
            transactionAdapter.addData(product.filter { it.stock!! > 0 })
        })
        viewModel.checkout.observe(this, {
            productCheckout = it
        })
    }


    private fun initRV() {
        transactionAdapter = TransactionAdapter { product ->
            viewModel.setProductCheckout(product)
        }

        _bind.rvProducts.apply {
            setHasFixedSize(true)
            adapter = transactionAdapter
        }
    }

    override fun onBackPressed() {
        val dialogMessage = getString(R.string.message_delete_product)
        val dialogTitle = getString(R.string.title_cancle_transaction)

        if (productCheckout.isNotEmpty()) {
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
        } else {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.transaction_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.order_menu -> {
                startActivity(Intent(this, OrderActivity::class.java))
                finish()
                return true
            }
        }
        return false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_checkout -> {
                startActivity(Intent(this, CheckoutActivity::class.java).apply {
                    putExtra(
                        CheckoutActivity.PRODUCTS_EXTRAS,
                        productCheckout as ArrayList<ProductCheckout>
                    )
                    putExtra(CheckoutActivity.IS_LOCAL, true)
                })
                finish()
            }
        }
    }
}