package com.gamehub.lite.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val iconUrl: String?,
    val genre: String,
    val rating: Float = 0f,
    val isInstalled: Boolean = false,
    val downloadUrl: String?,
    val fileSize: Long = 0L,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)