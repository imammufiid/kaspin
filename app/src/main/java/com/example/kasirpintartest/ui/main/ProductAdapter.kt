package com.example.kasirpintartest.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kasirpintartest.R
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.databinding.ProductItemLayoutBinding

class ProductAdapter(private val onClick: (Product, Int, Int) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    companion object {
        const val EDIT = 1
        const val DELETE = 2
    }

    var data = ArrayList<Product>()
    fun addData(data: List<Product>) {
        this.data.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.product_item_layout,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.binding.product = data[position]
        holder.binding.btnEdit.setOnClickListener {
            onClick(data[position], position, EDIT)
        }
        holder.binding.btnDelete.setOnClickListener {
            onClick(data[position], position, DELETE)
        }
    }

    override fun getItemCount() = data.size

    inner class ViewHolder(val binding: ProductItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun addItem(product: Product) {
        this.data.add(product)
        notifyItemInserted(this.data.size - 1)
    }

    fun updateItem(position: Int, product: Product) {
        this.data[position] = product
        notifyItemChanged(position, product)
    }

    fun removeItem(position: Int) {
        this.data.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.data.size)
    }
}