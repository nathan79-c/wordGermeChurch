package com.example.wordgermechurch.ui.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.LocalTextStyle
/* noinspection UsingMaterialAndMaterial3Libraries */
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordgermechurch.R


object ItemEditDestination : NavigationDestination {
    override val route = "item_edit"
    override val titleRes = R.string.edit_item_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}


@Composable
fun SimpleOutlinedTextFieldSample(
    itemUiState: ItemUiState,
    onDescriptionChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = itemUiState.itemDetails.description,
            onValueChange = onDescriptionChange,
            label = { Text("Reference Biblique") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
        )
        OutlinedTextField(
            value = itemUiState.itemDetails.content,
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
            Text(text = "Save")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleOutlinedTextFieldSamplePreview() {
    SimpleOutlinedTextFieldSample(
        itemUiState = ItemUiState(itemDetails = ItemDetails(description = "Example Description", content = "Example Content")),
        onDescriptionChange = {},
        onContentChange = {},
        onSaveClick = {}
    )
}


