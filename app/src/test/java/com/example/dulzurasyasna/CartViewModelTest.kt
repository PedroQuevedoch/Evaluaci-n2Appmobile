package com.example.dulzurasyasna

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.dulzurasyasna.data.InMemoryProductRepository
import com.example.dulzurasyasna.ui.CartViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {
    @Test
    fun addItems_updatesTotal() = runTest {
        val app: Application = ApplicationProvider.getApplicationContext()
        val vm = CartViewModel(app)
        val repo = InMemoryProductRepository()
        val p1 = repo.getProducts().first()

        vm.addToCart(p1)
        vm.addToCart(p1)

        val total = vm.state.value.total.toInt()
        assertEquals((p1.price * 2).toInt(), total)
    }
}


