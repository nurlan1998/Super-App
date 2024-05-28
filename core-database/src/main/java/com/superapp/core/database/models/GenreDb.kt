package com.superapp.core.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreDb(
    @PrimaryKey
    val id: String,
    val name: String? = ""
)
