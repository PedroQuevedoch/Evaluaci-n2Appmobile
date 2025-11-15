package com.example.dulzurasyasna.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dulzurasyasna.R
import com.example.dulzurasyasna.model.Product
import com.example.dulzurasyasna.ui.components.BackgroundImage

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailScreen(
    product: Product,
    onBack: () -> Unit,
    onAddToCart: (Int) -> Unit
) {
    val peopleOptions = listOf(10, 15, 25, 50)
    var selectedPeople by remember { mutableStateOf<Int?>(null) }
    var textFieldExpanded by remember { mutableStateOf(false) }

    BackgroundImage {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text(product.name) },
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
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = product.description, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = stringResource(id = R.string.price_prefix, product.price.toInt()), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(24.dp))
            
            // Dropdown para cantidad de personas
            ExposedDropdownMenuBox(
                expanded = textFieldExpanded,
                onExpandedChange = { textFieldExpanded = !textFieldExpanded }
            ) {
                OutlinedTextField(
                    value = selectedPeople?.let { stringResource(id = R.string.people_count, it) } ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.select_people_count)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = textFieldExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    placeholder = { Text(stringResource(id = R.string.select_people_count_hint)) }
                )
                DropdownMenu(
                    expanded = textFieldExpanded,
                    onDismissRequest = { textFieldExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    peopleOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.people_count, option)) },
                            onClick = {
                                selectedPeople = option
                                textFieldExpanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { 
                    selectedPeople?.let { onAddToCart(it) }
                },
                enabled = selectedPeople != null,
                modifier = Modifier.fillMaxWidth()
            ) { 
                Text(stringResource(id = R.string.add_to_cart)) 
            }
        }
        }
    }
}


