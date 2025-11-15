package com.example.dulzurasyasna.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.cartDataStore by preferencesDataStore(name = "cart_prefs")

class CartDataStore(private val context: Context) {
    private val CART_JSON = stringPreferencesKey("cart_json")

    suspend fun saveCart(json: String) {
        context.cartDataStore.edit { prefs ->
            prefs[CART_JSON] = json
        }
    }

    suspend fun loadCart(): String? {
        return context.cartDataStore.data.map { it[CART_JSON] }.first()
    }
}


