package com.example.wordgermechurch.ui.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.wordgermechurch.data.ItemsRepository

class UpdateViewModel(
    SavedStateHandle: SavedStateHandle,
    private val itemsRepository: ItemsRepository

) :ViewModel(){
    var itemUiState by mutableStateOf(ItemUiState())
        private set
  //  private val itemId: Int = checkNotNull(savedStateHandle[ItemEdit.itemIdArg])
}