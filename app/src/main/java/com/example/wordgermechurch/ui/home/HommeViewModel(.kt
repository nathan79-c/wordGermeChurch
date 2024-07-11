package com.example.wordgermechurch.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordgermechurch.R
import com.example.wordgermechurch.data.Item
import com.example.wordgermechurch.data.ItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Random

class HommeViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUistate())
    val uiState: StateFlow<HomeUistate> = _uiState

    init {
        getItem()
    }

    fun getItem() {
        viewModelScope.launch {
            itemsRepository.getItemStream()
                .filterNotNull()
                .map { HomeUistate(currentVerset = it, currentImage = getRandomImage()) }
                .collect { newState ->
                    _uiState.value = newState
                }
        }
    }
    fun incrementLiked(item: Item) {
        viewModelScope.launch {
            itemsRepository.updateLiked(item)
            // Mise à jour de l'état avec le nouvel item liké
            _uiState.value = _uiState.value.copy(
                currentVerset = item.copy(liked = item.liked + 1)
            )
        }
    }

    private fun getRandomImage(images: List<Int> = imagesBack): Int {
        val random = Random()
        val index = random.nextInt(images.size)
        return images[index]
    }
}





/**
 * Ui State for HomeScreen
 */
data class HomeUistate(
    var currentVerset: Item? = null,
    var currentImage: Int = R.drawable.image2
)

// Sample list of images
val imagesBack = listOf(
    R.drawable.first,
    R.drawable.image2,
    R.drawable.image10,
    R.drawable.image3,
    R.drawable.image6,
    R.drawable.image7,
    R.drawable.image8,
    R.drawable.image9,
    R.drawable.image10,
    R.drawable.fourthy,
    R.drawable.fivety,
)


