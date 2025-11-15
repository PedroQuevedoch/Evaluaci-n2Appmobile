package com.example.dulzurasyasna.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dulzurasyasna.R
import com.example.dulzurasyasna.ui.CartViewModel
import com.example.dulzurasyasna.ui.components.BackgroundImage
import com.example.dulzurasyasna.utils.NotificationUtils

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CheckoutScreen(
    cartViewModel: CartViewModel,
    onBack: () -> Unit,
    onConfirm: () -> Unit
) {
    val state = cartViewModel.state.collectAsState().value
    val context = LocalContext.current
    BackgroundImage {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.checkout_title)) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.back)
                            )
                        }
                    }
                )
            }
        ) { inner ->
        Column(modifier = Modifier.padding(inner).padding(16.dp).fillMaxSize()) {
            Text(text = stringResource(id = R.string.checkout_summary, state.items.size, state.total.toInt()))
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Mostrar notificación (recurso nativo)
                    NotificationUtils.showOrderConfirmationNotification(context, state.total)
                    onConfirm()
                },
                enabled = state.items.isNotEmpty()
            ) {
                Text(stringResource(id = R.string.confirm_order))
            }
        }
        }
    }
}


