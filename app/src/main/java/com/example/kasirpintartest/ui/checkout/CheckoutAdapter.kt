package com.example.kasirpintartest.ui.checkout

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kasirpintartest.R
import com.example.kasirpintartest.data.entity.ProductCheckout
import com.example.kasirpintartest.databinding.CheckoutItemLayoutBinding


class CheckoutAdapter(private val onClick: (ProductCheckout, Int) -> Unit) :
    RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {
    var data = ArrayList<ProductCheckout>()
    fun addData(data: List<ProductCheckout>?) {
        this.data.apply {
            clear()
            if (data != null) {
                addAll(data)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.checkout_item_layout,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CheckoutAdapter.ViewHolder, position: Int) {
        holder.binding.product = data[position]
        holder.binding.btnDelete.setOnClickListener {
            onClick(data[position], position)
        }
    }

    override fun getItemCount() = data.size

    inner class ViewHolder(val binding: CheckoutItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun updateItem(position: Int, product: ProductCheckout) {
        this.data[position] = product
        notifyItemChanged(position, product)
    }

    fun removeItem(position: Int) {
        this.data.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.data.size)
    }
}