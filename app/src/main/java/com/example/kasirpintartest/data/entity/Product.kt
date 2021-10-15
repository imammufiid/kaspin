package com.example.kasirpintartest.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var id: Int? = null,
    var code: String? = null,
    var name: String? = null,
    var stock: Int? = null,
    var date: String? = null
) : Parcelable

@Parcelize
data class ProductCheckout(
    var product: Product? = null,
    var qty: Int? = 0
): Parcelable

@Parcelize
data class Order(
    var id: String? = null,
    var product: List<ProductCheckout>? = null,
): Parcelable