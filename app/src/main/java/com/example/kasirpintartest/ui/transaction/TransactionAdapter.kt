package com.example.kasirpintartest.ui.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kasirpintartest.R
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.transaction_item_layout,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TransactionAdapter.ViewHolder, position: Int) {
        holder.binding.product = data[position]
        holder.binding.btnCart.setOnClickListener {
            onClick(data[position])
        }
    }

    override fun getItemCount() = data.size

    inner class ViewHolder(val binding: TransactionItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}