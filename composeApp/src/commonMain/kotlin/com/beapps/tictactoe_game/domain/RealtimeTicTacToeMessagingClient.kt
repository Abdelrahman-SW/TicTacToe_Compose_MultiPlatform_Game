package com.beapps.tictactoe_game.domain

import com.beapps.tictactoe_game.domain.models.GameState
import com.beapps.tictactoe_game.domain.models.PlayerTurn
import kotlinx.coroutines.flow.Flow

interface RealtimeTicTacToeMessagingClient {
    suspend fun connectToWebSocket(username: String) : Boolean
    fun getGameStateStream(): Flow<GameState>
    suspend fun sendPlayerTurn(action: PlayerTurn)
    suspend fun disconnect()

    companion object {
        const val BASE_URL = "ws://172.30.176.1:8080/"
        const val TicTacToe_SOCKET_ENDPOINT = "play"
    }
}