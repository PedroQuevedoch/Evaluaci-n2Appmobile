package com.example.dulzurasyasna.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dulzurasyasna.R
import com.example.dulzurasyasna.model.Product
import com.example.dulzurasyasna.ui.components.BackgroundImage
import androidx.compose.material3.TextField

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(
    products: List<Product>,
    onProductClick: (String) -> Unit,
    onCartClick: () -> Unit,
    onAboutClick: () -> Unit,
) {
    val (query, setQuery) = remember { mutableStateOf("") }
    val filtered = products.filter { it.name.contains(query, ignoreCase = true) }

    BackgroundImage {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    actions = {
                        IconButton(onClick = onAboutClick) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_info_details),
                                contentDescription = stringResource(id = R.string.label_about)
                            )
                        }
                        IconButton(onClick = onCartClick) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_view),
                                contentDescription = stringResource(id = R.string.label_cart)
                            )
                        }
                    }
                )
            }
        ) { inner ->
        Column(modifier = Modifier.padding(inner)) {
            TextField(
                value = query,
                onValueChange = setQuery,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
                singleLine = true
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = filtered,
                    key = { it.id }
                ) { product ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onProductClick(product.id) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = product.imageResId),
                            contentDescription = product.name,
                            modifier = Modifier,
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = product.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                            Text(text = "${product.price.toInt()} CLP", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
        }
    }
}


