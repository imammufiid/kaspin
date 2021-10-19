package com.example.kasirpintartest.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasirpintartest.R
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.databinding.ActivityMainBinding
import com.example.kasirpintartest.ui.addupdate.AddUpdateActivity
import com.example.kasirpintartest.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var _bind: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private var position: Int = 0

    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory.getInstance(this))[MainViewModel::class.java]
    }

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.data != null) {
            when (result.resultCode) {
                AddUpdateActivity.RESULT_ADD -> {
                    val product =
                        result.data?.getParcelableExtra<Product>(AddUpdateActivity.EXTRA_PRODUCT) as Product
                    productAdapter.addItem(product)
                    _bind.rvProducts.smoothScrollToPosition(productAdapter.itemCount - 1)
                    showSnackbarMessage(getString(R.string.success_insert_product_message))
                }
                AddUpdateActivity.RESULT_UPDATE -> {
                    val product =
                        result.data?.getParcelableExtra<Product>(AddUpdateActivity.EXTRA_PRODUCT) as Product
                    val position =
                        result?.data?.getIntExtra(AddUpdateActivity.EXTRA_POSITION, 0) as Int
                    productAdapter.updateItem(position, product)
                    _bind.rvProducts.smoothScrollToPosition(position)
                    showSnackbarMessage(getString(R.string.success_update_product_message))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.title = "Product"

        _bind.btnAdd.setOnClickListener(this)

        initRV()
        observeVM()
        viewModel.getProducts()
    }

//    override fun onResume() {
//        super.onResume()
//        viewModel.getProducts()
//    }

    private fun observeVM() {
        viewModel.loading.observe(this, {
            if (it) {
                _bind.progressbar.visibility = View.VISIBLE
            } else {
                _bind.progressbar.visibility = View.GONE
            }
        })
        viewModel.products.observe(this, {
            productAdapter.addData(it)
        })
        viewModel.deleted.observe(this, { result ->
            if (result > 0) {
                showSnackbarMessage(getString(R.string.success_delete_product_message))
                productAdapter.removeItem(position)
            } else {
                showSnackbarMessage(getString(R.string.failed_delete_product_message))
            }
        })
    }


    private fun initRV() {
        val intent = Intent(this, AddUpdateActivity::class.java)

        productAdapter = ProductAdapter { product, position, status ->
            if (status == ProductAdapter.EDIT) {
                intent.putExtra(AddUpdateActivity.EXTRA_PRODUCT, product)
                intent.putExtra(AddUpdateActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            } else if (status == ProductAdapter.DELETE) {
                this.position = position
                val dialogMessage = getString(R.string.message_delete_product)
                val dialogTitle = getString(R.string.delete_product)

                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle(dialogTitle)
                alertDialogBuilder
                    .setMessage(dialogMessage)
                    .setCancelable(false)
                    .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        viewModel.deleteProduct(product)
                    }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }

        }

        _bind.rvProducts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = productAdapter
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add -> startActivity(Intent(this, AddUpdateActivity::class.java))
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(_bind.rvProducts, message, Snackbar.LENGTH_SHORT).show()
    }
}