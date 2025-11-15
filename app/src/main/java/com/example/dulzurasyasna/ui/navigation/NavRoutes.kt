package com.example.dulzurasyasna.ui.navigation

object NavRoutes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val HOME = "home"
    const val DETAIL = "detail/{productId}"
    const val CART = "cart"
    const val CHECKOUT = "checkout"
    const val ABOUT = "about"

    fun detail(productId: String) = "detail/$productId"
}


