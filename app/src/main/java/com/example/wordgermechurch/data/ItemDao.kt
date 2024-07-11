package com.example.wordgermechurch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao{
@Insert(onConflict = OnConflictStrategy.IGNORE)
suspend fun insert(item: Item)

@Insert(onConflict = OnConflictStrategy.IGNORE)
suspend fun insertAll(items: List<Item>)

@Update
suspend fun update(item: Item)

@Delete
suspend fun delete(item: Item)

@Query("SELECT * FROM Versets ORDER BY RANDOM() LIMIT 1;")
fun getRandomItem():Flow<Item>

@Query("SELECT * FROM Versets where liked >=1 ")
fun  getLikedVersets():Flow<List<Item>>

@Query("SELECT * FROM Versets")
fun getAllVersets():Flow<List<Item>>

    @Query("UPDATE Versets SET liked = liked + 1 WHERE id = :itemId")
    suspend fun incrementLiked(itemId: Int)

}
