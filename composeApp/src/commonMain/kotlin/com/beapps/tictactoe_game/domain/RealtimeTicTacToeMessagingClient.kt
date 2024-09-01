package com.beapps.tictactoe_game.domain

import com.beapps.tictactoe_game.domain.models.GameState
import com.beapps.tictactoe_game.domain.models.PlayerTurn
import kotlinx.coroutines.flow.Flow

interface RealtimeTicTacToeMessagingClient {
    suspend fun getSessionIsActiveState(): Flow<Boolean>
    suspend fun connectToWebSocket(username: String) : Boolean
    fun getGameStateStream(): Flow<GameState>
    suspend fun sendPlayerTurn(action: PlayerTurn)
    suspend fun disconnect()

    companion object {
        // 172.23.64.1 : is my device ip address replace it with your own ip
        const val BASE_URL = "ws://172.23.64.1:8080/"
        const val TicTacToe_SOCKET_ENDPOINT = "play"
    }
}