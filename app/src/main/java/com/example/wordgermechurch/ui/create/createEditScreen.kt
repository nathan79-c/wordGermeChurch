package com.example.wordgermechurch.ui.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordgermechurch.ui.AppViewModelProvider
import kotlinx.coroutines.launch


@Composable
fun SimpleOutlinedTextFieldSample(
    viewmodel: InsertViewmodel = viewModel(factory = AppViewModelProvider.Factory),
    navigateHome: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val itemUiState by viewmodel.itemUiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = itemUiState.itemDetails.description,
            onValueChange = { viewmodel.updateUiState(itemUiState.itemDetails.copy(description = it)) },
            label = { Text("Reference Biblique") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
        )
        OutlinedTextField(
            value = itemUiState.itemDetails.content,
            onValueChange = { viewmodel.updateUiState(itemUiState.itemDetails.copy(content = it)) },
            label = { Text("Contenu du Verset") },
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    viewmodel.saveItem()
                    navigateHome()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun ItemEditScreen(
    itemId: String,
    navigateBack: () -> Unit
) {
    // Contenu pour éditer l'item
    // Utilisez itemId pour charger les données de l'item et les afficher pour modification
}


@Preview(showBackground = true)
@Composable
fun PreviewEdit(){
   // SimpleOutlinedTextFieldSample()
}