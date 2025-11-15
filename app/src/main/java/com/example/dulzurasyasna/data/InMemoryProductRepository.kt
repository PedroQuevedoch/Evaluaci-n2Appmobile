package com.example.dulzurasyasna.data

import com.example.dulzurasyasna.R
import com.example.dulzurasyasna.model.Product

class InMemoryProductRepository {
    private val products: List<Product> = listOf(
        Product(
            id = "cake-1",
            name = "Torta Tres Leches",
            description = "Bizcocho esponjoso bañado en tres leches, coronado con crema.",
            price = 18990.0,
            imageResId = R.drawable.ic_launcher_foreground
        ),
        Product(
            id = "cake-2",
            name = "Torta Chocolate",
            description = "Capas de chocolate con ganache intenso y decoración simple.",
            price = 20990.0,
            imageResId = R.drawable.ic_launcher_foreground
        ),
        Product(
            id = "cake-3",
            name = "Cheesecake Frutilla",
            description = "Cheesecake clásico con coulis de frutilla.",
            price = 17990.0,
            imageResId = R.drawable.ic_launcher_foreground
        ),
        Product(
            id = "cake-4",
            name = "Torta Manjar Lucuma",
            description = "Deliciosa torta con manjar y crema de lúcuma, un sabor único.",
            price = 19990.0,
            imageResId = R.drawable.ic_launcher_foreground
        ),
        Product(
            id = "cake-5",
            name = "Selva Negra",
            description = "Torta clásica alemana con chocolate, cerezas y crema batida.",
            price = 21990.0,
            imageResId = R.drawable.ic_launcher_foreground
        ),
        Product(
            id = "cake-6",
            name = "Panqueque Naranja",
            description = "Suaves panqueques apilados con crema de naranja y glaseado cítrico.",
            price = 16990.0,
            imageResId = R.drawable.ic_launcher_foreground
        )
    )

    fun getProducts(): List<Product> = products
    fun getProductById(id: String): Product? = products.find { it.id == id }
}


