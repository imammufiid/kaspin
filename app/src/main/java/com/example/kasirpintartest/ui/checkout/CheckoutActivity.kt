package com.example.kasirpintartest.ui.checkout

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasirpintartest.R
import com.example.kasirpintartest.data.entity.Order
import com.example.kasirpintartest.data.entity.ProductCheckout
import com.example.kasirpintartest.databinding.ActivityCheckoutBinding
import com.example.kasirpintartest.ui.transactionsuccess.TransactionSuccessActivity
import com.example.kasirpintartest.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class CheckoutActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var _bind: ActivityCheckoutBinding
    private var products: MutableList<ProductCheckout>? = null
    private lateinit var checkoutAdapter: CheckoutAdapter
    private lateinit var progressDialog: ProgressDialog
    private lateinit var dbRef: DatabaseReference
    private var isLocalSource: Boolean? = true
    private var orderID: String? = ""

    companion object {
        const val PRODUCTS_EXTRAS = "products_extras"
        const val IS_LOCAL = "is_local"
        const val ORDER_ID = "order_id"
    }

    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory.getInstance(this))[CheckoutViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = DataBindingUtil.setContentView(this, R.layout.activity_checkout)
        supportActionBar?.title = getString(R.string.checkout_title)

        _bind.btnCancel.setOnClickListener(this)
        _bind.btnSubmit.setOnClickListener(this)
        _bind.btnSave.setOnClickListener(this)

        products = intent.getParcelableArrayListExtra(PRODUCTS_EXTRAS)
        isLocalSource = intent.getBooleanExtra(IS_LOCAL, true)
        orderID = intent.getStringExtra(ORDER_ID)

        dbRef = FirebaseDatabase.getInstance().getReference("Orders")
        progressDialog = ProgressDialog(this)
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
                products?.removeAt(position)
            }
        }

        if (isLocalSource == true) {
            checkoutAdapter.addData(products)
        } else {
            dbRef.child(orderID.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val productItem = snapshot.getValue(Order::class.java)
                            if (productItem != null) {
                                checkoutAdapter.addData(productItem.product)
                                products = productItem.product as MutableList<ProductCheckout>
                            }

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        showSnackbarMessage(error.message)
                    }
                })
        }

        _bind.rvProducts.apply {
            setHasFixedSize(true)
            adapter = checkoutAdapter
        }
    }

    private fun observeVM() {
        viewModel.updated.observe(this, { result ->
            if (result <= 0) {
                Toast.makeText(this, getString(R.string.failed_transaction), Toast.LENGTH_SHORT)
                    .show()
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
                finish()
            }
            R.id.btn_save -> {
                progressDialog.apply {
                    setMessage("Save to database")
                }.show()

                if (orderID?.isNotEmpty() == true) {
                    val order = Order(orderID, products)
                     dbRef.child(orderID.toString()).setValue(order)
                         .addOnCompleteListener {
                             progressDialog.dismiss()
                             showSnackbarMessage("Order updated in to firebase")
                         }
                } else {
                    val orderId = "ODR_${System.currentTimeMillis()}"
                    val order = Order(orderId, products)
                    dbRef.child(orderId).setValue(order)
                        .addOnCompleteListener {
                            progressDialog.dismiss()
                            showSnackbarMessage("Order saved in to firebase")
                        }
                        .addOnFailureListener {
                            progressDialog.dismiss()
                            it.message?.let { msg -> showSnackbarMessage(msg) }
                        }
                }

            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(_bind.rvProducts, message, Snackbar.LENGTH_SHORT).show()
    }

}