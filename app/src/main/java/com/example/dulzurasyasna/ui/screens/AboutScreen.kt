package com.example.dulzurasyasna.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dulzurasyasna.R
import com.example.dulzurasyasna.ui.components.BackgroundImage

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AboutScreen(onBack: () -> Unit) {
    BackgroundImage {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.about_title)) },
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
        Column(modifier = Modifier.padding(inner).padding(16.dp)) {
            Text(text = stringResource(id = R.string.about_text))
            Text(text = stringResource(id = R.string.contact_text))
        }
        }
    }
}


