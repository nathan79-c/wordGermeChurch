package com.example.wordgermechurch.data

import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository (private  val itemDao: ItemDao):ItemsRepository {
    override fun getItemStream(): Flow<Item> = itemDao.getRandomItem()

    override fun getLikedVersetsStream(): Flow<List<Item>> = itemDao.getLikedVersets()
    override  fun getAlItemStream():Flow<List<Item>> = itemDao.getAllVersets()

    override fun getItemfindStream(id: Int): Flow<Item?> = itemDao.getItem(id)

    override suspend fun insertItem(item: Item) = itemDao.insert(item)

    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    override suspend fun updateItem(item: Item) = itemDao.update(item)

    override suspend fun updateLiked(item: Item) =itemDao.incrementLiked(item.id)


}