package com.example.kasirpintartest.utils

import com.example.kasirpintartest.data.entity.Order
import com.example.kasirpintartest.data.entity.Product
import com.example.kasirpintartest.data.entity.ProductCheckout

object DataDummy {
    fun generateProducts(): ArrayList<Product> {
        val products = ArrayList<Product>()
        for (i in 0..10) {
            products.add(Product(i, "${i}_123", "${i}_asdf", i, "123"))
        }
        return products
    }

    fun generateProductCheckout(): ArrayList<ProductCheckout> {
        val products = ArrayList<ProductCheckout>()
        for (i in 0..10) {
            products.add(
                ProductCheckout(
                    product = Product(i, "${i}_123", "${i}_asdf", i, "123"),
                    qty = i
                )
            )
        }
        return products
    }

    fun generateOrders(): ArrayList<Order> {
        val orders = ArrayList<Order>()
        for (i in 0..10) {
            orders.add(Order(i.toString(), generateProductCheckout()))
        }
        return orders
    }
}