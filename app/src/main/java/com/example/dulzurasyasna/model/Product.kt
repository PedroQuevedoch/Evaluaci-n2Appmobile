package com.example.dulzurasyasna.model

import androidx.annotation.DrawableRes

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    @DrawableRes val imageResId: Int
)


