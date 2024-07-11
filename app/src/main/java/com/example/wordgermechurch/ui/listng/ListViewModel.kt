package com.example.wordgermechurch.ui.listng

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordgermechurch.data.Item
import com.example.wordgermechurch.data.ItemsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ListViewModel(private val itemsRepository: ItemsRepository): ViewModel() {
    val listUiState: StateFlow<ListUiState> =
        itemsRepository.getAlItemStream().map { ListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ListUiState()
            )
    val likedVersets: StateFlow<ListUiState> =
        itemsRepository.getLikedVersetsStream().map { ListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ListUiState()
            )


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteItem(item: Item) {
        itemsRepository.deleteItem(item)
    }
}


/**
 * Ui State for HomeScreen
 */
data class ListUiState(val itemList: List<Item> = listOf())