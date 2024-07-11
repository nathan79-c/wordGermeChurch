package com.example.wordgermechurch.ui.listng

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordgermechurch.data.Item
import com.example.wordgermechurch.ui.AppViewModelProvider
import kotlinx.coroutines.launch


@Composable
fun ListCard(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val itemListState by viewModel.listUiState.collectAsState()
    val itemList = itemListState.itemList // Extract the list of items
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(itemList) { item ->
            ItemCard(modifier,item = item, viewModel = viewModel)
        }
    }
}

@Composable
fun ItemCard(
    modifier: Modifier ,
    item: Item,
    viewModel: ListViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 8.dp),
    ) {
        Column {
            Text(
                text = "Allons Vers Eux Aimons Comme Dieu",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                    color = LocalContentColor.current.copy(alpha = 0.5f)
                )
            }
            Text(
                text = item.content,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                modifier = Modifier.padding(16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /* Implement edit functionality here */ }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Modifier")
                }
                IconButton(onClick = {
                    coroutineScope.launch {
                        viewModel.deleteItem(item)
                    }
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Supprimer")
                }
            }
        }
    }
}

@Preview
@Composable
fun CardPreview(){
   // SoupCard()
}
