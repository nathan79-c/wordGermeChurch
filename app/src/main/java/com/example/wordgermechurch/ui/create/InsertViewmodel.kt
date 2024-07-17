package com.example.wordgermechurch.ui.create


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordgermechurch.data.Item
import com.example.wordgermechurch.data.ItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InsertViewmodel(private val itemsRepository: ItemsRepository) : ViewModel() {
    private val _itemUiState = MutableStateFlow(ItemUiState())
    val itemUiState: StateFlow<ItemUiState> = _itemUiState.asStateFlow()

    fun updateUiState(itemDetails: ItemDetails) {
        _itemUiState.value =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    private fun validateInput(uiState: ItemDetails = _itemUiState.value.itemDetails): Boolean {
        return with(uiState) {
            description.isNotBlank() && content.isNotBlank()
        }
    }

    fun saveItem() {
        viewModelScope.launch {
            if (validateInput()) {
                itemsRepository.insertItem(_itemUiState.value.itemDetails.toItem())
            }
        }
    }

}

data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

data class ItemDetails(
    val id: Int = 0,
    val description: String = "",
    val content: String = ""
)

fun ItemDetails.toItem(): Item = Item(
    id = id,
    description = description,
    content = content
)

fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    description = description,
    content = content
)
