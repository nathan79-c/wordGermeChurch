package com.example.wordgermechurch.data

import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun getLikedVersetsStream(): Flow<List<Item>>

    fun getAlItemStream(): Flow<List<Item>>

    fun getItemStream(): Flow<Item>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: Item)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: Item)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: Item)

    suspend fun updateLiked(item: Item)
    fun getItemfindStream(id: Int): Flow<Item?>



}