package com.gamehub.lite.di

import android.content.Context
import androidx.room.Room
import com.gamehub.lite.data.local.GameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideGameDatabase(
        @ApplicationContext context: Context
    ): GameDatabase = Room.databaseBuilder(
        context,
        GameDatabase::class.java,
        GameDatabase.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideGameDao(database: GameDatabase) = database.gameDao()
}