package com.example.wordgermechurch.data

import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity(tableName = "Versets")
    data class Item(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val description:String,
        val content:String,
        val liked:Int = 0
    )