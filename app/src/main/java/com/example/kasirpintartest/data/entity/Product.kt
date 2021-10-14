package com.example.kasirpintartest.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product (
    val id: Int,
    val code: String,
    val name: String,
    val stock: Int,
    var date: String?
): Parcelable