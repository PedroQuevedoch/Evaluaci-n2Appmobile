package com.example.dulzurasyasna

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dulzurasyasna.ui.CartViewModel
import com.example.dulzurasyasna.ui.navigation.AppNavGraph
import com.example.dulzurasyasna.ui.theme.DulzurasYasnaTheme
import com.example.dulzurasyasna.utils.NotificationUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Crear canal de notificaciones
        NotificationUtils.createNotificationChannel(this)
        
        setContent {
            DulzurasYasnaTheme {
                val cartViewModel: CartViewModel = viewModel()
                Surface(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
                    AppNavGraph(cartViewModel = cartViewModel)
                }
            }
        }
    }
}
