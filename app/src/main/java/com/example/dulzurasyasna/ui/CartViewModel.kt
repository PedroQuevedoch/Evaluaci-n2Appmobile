package com.example.dulzurasyasna.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dulzurasyasna.data.CartDataStore
import com.example.dulzurasyasna.data.InMemoryProductRepository
import com.example.dulzurasyasna.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class CartItem(val product: Product, val quantity: Int, val numberOfPeople: Int)

data class CartState(
    val items: List<CartItem> = emptyList()
) {
    val total: Double get() = items.sumOf { it.product.price * it.quantity }
}

@Serializable
private data class CartItemDTO(val productId: String, val quantity: Int, val numberOfPeople: Int)

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    private val dataStore = CartDataStore(application.applicationContext)
    private val repo = InMemoryProductRepository()

    init {
        viewModelScope.launch(Dispatchers.IO) { loadFromStorage() }
    }

    fun addToCart(product: Product, numberOfPeople: Int) {
        viewModelScope.launch {
            _state.update { current ->
                val existing = current.items.find { it.product.id == product.id && it.numberOfPeople == numberOfPeople }
                val updatedItems = if (existing != null) {
                    current.items.map {
                        if (it.product.id == product.id && it.numberOfPeople == numberOfPeople) 
                            it.copy(quantity = it.quantity + 1) 
                        else it
                    }
                } else {
                    current.items + CartItem(product, 1, numberOfPeople)
                }
                current.copy(items = updatedItems)
            }
            persist()
        }
    }

    fun removeFromCart(productId: String, numberOfPeople: Int) {
        _state.update { current -> 
            current.copy(items = current.items.filterNot { 
                it.product.id == productId && it.numberOfPeople == numberOfPeople 
            }) 
        }
        viewModelScope.launch { persist() }
    }

    fun clearCart() {
        _state.update { CartState() }
        viewModelScope.launch { persist() }
    }

    private suspend fun loadFromStorage() {
        val json = dataStore.loadCart() ?: return
        val dtos = runCatching { Json.decodeFromString<List<CartItemDTO>>(json) }.getOrNull() ?: return
        val items = dtos.mapNotNull { dto ->
            val product = repo.getProductById(dto.productId) ?: return@mapNotNull null
            CartItem(product, dto.quantity, dto.numberOfPeople)
        }
        _state.update { it.copy(items = items) }
    }

    private suspend fun persist() {
        val dtos = state.value.items.map { CartItemDTO(it.product.id, it.quantity, it.numberOfPeople) }
        val json = Json.encodeToString(dtos)
        dataStore.saveCart(json)
    }
}


