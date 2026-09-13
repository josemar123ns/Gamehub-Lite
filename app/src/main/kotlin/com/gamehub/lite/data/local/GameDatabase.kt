package com.gamehub.lite.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gamehub.lite.data.model.GameEntity

@Database(
    entities = [GameEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        const val DATABASE_NAME = "gamehub_lite.db"
    }
}