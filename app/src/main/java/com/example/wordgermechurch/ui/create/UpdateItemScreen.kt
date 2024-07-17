package com.example.wordgermechurch.ui.create

//noinspection UsingMaterialAndMaterial3Libraries
/* noinspection UsingMaterialAndMaterial3Libraries */
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.LocalTextStyle
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordgermechurch.R
import com.example.wordgermechurch.ui.AppViewModelProvider
import com.example.wordgermechurch.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch


object ItemEditDestination : NavigationDestination {
    override val route = "item_edit"
    override val titleRes = R.string.edit_item_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun ItemEditScreen(
    itemId: Int,
    navigateBack: () -> Unit,
    viewModel: UpdateViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var description by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    LaunchedEffect(itemId) {
        viewModel.loadItem(itemId)
    }

    val itemUiState = viewModel.itemUiState

    LaunchedEffect(itemUiState) {
        description = itemUiState.itemDetails.description
        content = itemUiState.itemDetails.content
    }

    SimpleOutlinedTextFieldSample(
        description = description,
        content = content,
        onDescriptionChange = { description = it },
        onContentChange = { content = it },
        onSaveClick = {
            coroutineScope.launch {
                viewModel.updateItem(description, content)
                navigateBack()
            }
        }
    )
}


@Composable
fun SimpleOutlinedTextFieldSample(
    description: String,
    content: String,
    onDescriptionChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Reference Biblique") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
        )
        OutlinedTextField(
            value = content,
            onValueChange = onContentChange,
            label = { Text("Contenu du Verset") },
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onSaveClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Modifier")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun SimpleOutlinedTextFieldSamplePreview() {

}


