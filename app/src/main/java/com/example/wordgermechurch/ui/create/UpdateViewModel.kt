package com.example.wordgermechurch.ui.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordgermechurch.data.ItemsRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])

    fun loadItem(itemId: Int ) {
        viewModelScope.launch {
            itemUiState = itemsRepository.getItemfindStream(itemId)
                .filterNotNull()
                .first()
                .toItemUiState(true)
        }
    }

    suspend fun updateItem(description: String, content: String) {
        val updatedItemDetails = itemUiState.itemDetails.copy(description = description, content = content)
        if (validateInput(updatedItemDetails)) {
            itemsRepository.updateItem(updatedItemDetails.toItem())
            itemUiState = itemUiState.copy(itemDetails = updatedItemDetails)
        }
    }

    private fun validateInput(uiState: ItemDetails): Boolean {
        return with(uiState) {
            content.isNotBlank() && description.isNotBlank()
        }
    }
}


