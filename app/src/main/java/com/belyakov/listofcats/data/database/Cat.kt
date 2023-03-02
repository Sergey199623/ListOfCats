package com.belyakov.listofcats.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats")
data class Cat(
    @PrimaryKey val id: String,
    val url: String,
    var isFavorite: Boolean = false
)