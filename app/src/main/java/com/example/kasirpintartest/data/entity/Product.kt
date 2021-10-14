package com.example.kasirpintartest.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product (
    var id: Int,
    var code: String,
    var name: String,
    var stock: Int,
    var date: String?
): Parcelable