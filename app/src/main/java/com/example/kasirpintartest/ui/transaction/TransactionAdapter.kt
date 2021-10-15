package com.example.kasirpintartest.ui.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.databinding.TransactionItemLayoutBinding


class TransactionAdapter(private val onClick: (Product) -> Unit) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    var data = ArrayList<Product>()
    fun addData(data: List<Product>) {
        this.data.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapter.ViewHolder {
        val binding =
            TransactionItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class ViewHolder(private val binding: TransactionItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Product) {
            with(binding) {
                tvItemName.text = data.name
                tvItemCode.text = data.code
                btnCart.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }
}