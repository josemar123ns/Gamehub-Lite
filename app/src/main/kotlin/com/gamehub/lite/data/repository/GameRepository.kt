package com.gamehub.lite.data.repository

import com.gamehub.lite.data.local.GameDao
import com.gamehub.lite.data.model.GameEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val gameDao: GameDao
) {
    fun getAllGames(): Flow<List<GameEntity>> = gameDao.getAllGames()

    suspend fun getGameById(id: String): GameEntity? = gameDao.getGameById(id)

    suspend fun insertGame(game: GameEntity) = gameDao.insertGame(game)

    suspend fun updateGame(game: GameEntity) = gameDao.updateGame(game)

    suspend fun deleteGame(game: GameEntity) = gameDao.deleteGame(game)

    suspend fun deleteAllGames() = gameDao.deleteAllGames()
}