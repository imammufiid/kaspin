package com.example.kasirpintartest.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
            if (data != null) {
                addAll(data)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val binding =
            ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class ViewHolder(private val binding: ProductItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Product) {
            with(binding) {
                tvItemName.text = data.name
                tvItemCode.text = data.code
                tvItemStock.text = "Stok: "+data.stock.toString()
                btnEdit.setOnClickListener {
                    onClick(data, adapterPosition, EDIT)
                }
                btnDelete.setOnClickListener {
                    onClick(data, adapterPosition, DELETE)
                }
            }
        }
    }

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