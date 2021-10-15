package com.example.kasirpintartest.ui.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutAdapter.ViewHolder {
        val binding =
            CheckoutItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckoutAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class ViewHolder(private val binding: CheckoutItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductCheckout) {
            with(binding) {
                tvItemName.text = data.product?.name
                tvItemCode.text = data.product?.code
                tvQty.text = "Jml: "+ data.qty.toString()
                btnDelete.setOnClickListener {
                    onClick(data, adapterPosition)
                }
            }
        }
    }

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