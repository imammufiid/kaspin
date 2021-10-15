package com.example.kasirpintartest.ui.order

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasirpintartest.R
import com.example.kasirpintartest.data.entity.Order
import com.example.kasirpintartest.databinding.ActivityOrderBinding
import com.example.kasirpintartest.ui.checkout.CheckoutActivity
import com.google.firebase.database.*

class OrderActivity : AppCompatActivity() {
    private lateinit var _bind: ActivityOrderBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(_bind.root)
        supportActionBar?.title = "Order"

        dbRef = FirebaseDatabase.getInstance().getReference("Orders")
        initRV()
        getOrder()
    }

    private fun getOrder() {
        _bind.progressbar.visibility = View.VISIBLE
        val orders: MutableList<Order> = mutableListOf()
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _bind.progressbar.visibility = View.GONE
                if (snapshot.exists()) {
                    _bind.tvMessage.visibility = View.GONE
                    for (order in snapshot.children) {
                        val productItem = order.getValue(Order::class.java)
                        if (productItem != null) {
                            orders.add(productItem)
                        }
                    }
                    orderAdapter.addData(orders)
                } else {
                    _bind.tvMessage.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _bind.progressbar.visibility = View.GONE
                Toast.makeText(this@OrderActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRV() {
        orderAdapter = OrderAdapter { order, status, position ->
            if (status == OrderAdapter.LOAD) {
                startActivity(Intent(this, CheckoutActivity::class.java).apply {
                    putExtra(CheckoutActivity.ORDER_ID, order.id)
                    putExtra(CheckoutActivity.IS_LOCAL, false)
                })
                finish()
            } else if (status == OrderAdapter.DELETE) {
                val dialogMessage = getString(R.string.message_delete_product)
                val dialogTitle = getString(R.string.title_cancle_transaction)

                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle(dialogTitle)
                alertDialogBuilder
                    .setMessage(dialogMessage)
                    .setCancelable(false)
                    .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        dbRef.child(order.id.toString()).removeValue()
                        orderAdapter.removeItem(position)
                    }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()

            }

        }

        _bind.rvOrder.apply {
            layoutManager = LinearLayoutManager(this@OrderActivity)
            setHasFixedSize(true)
            adapter = orderAdapter
        }
    }
}