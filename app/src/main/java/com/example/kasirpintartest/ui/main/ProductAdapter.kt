package com.example.kasirpintartest.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.databinding.ProductItemLayoutBinding

class ProductAdapter(private val onClick: (Product) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    var data = ArrayList<Product>()
        set(data) {
            if (data.size > 0) {
                data.clear()
            }
            data.addAll(data)
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
                tvItemCode.text = data.date
                tvItemStock.text = data.stock.toString()
            }
        }
    }

//    fun addAll(data: List<Product>) {
//        this.data.apply {
//            this.clear()
//            addAll(data)
//        }
//        notifyDataSetChanged()
//    }

    fun addItem(note: Product) {
        this.data.add(note)
        notifyItemInserted(this.data.size - 1)
    }

    fun updateItem(position: Int, note: Product) {
        this.data[position] = note
        notifyItemChanged(position, note)
    }

    fun removeItem(position: Int) {
        this.data.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.data.size)
    }
}