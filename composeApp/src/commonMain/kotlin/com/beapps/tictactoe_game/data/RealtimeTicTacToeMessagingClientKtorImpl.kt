package com.beapps.tictactoe_game.data

import com.beapps.tictactoe_game.data.models.GameStateDto
import com.beapps.tictactoe_game.domain.models.PlayerTurn
import com.beapps.tictactoe_game.domain.models.GameState
import com.beapps.tictactoe_game.domain.RealtimeTicTacToeMessagingClient
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RealtimeTicTacToeMessagingClientKtorImpl(
    private val client: HttpClient
) : RealtimeTicTacToeMessagingClient {



    private var socketSession: WebSocketSession? = null
    override suspend fun getSessionIsActiveState(): Flow<Boolean> = flow {
        while (socketSession!=null) {
            val isSessionActive = socketSession?.isActive == true
            emit(isSessionActive)
            delay(1000) // Check every second, adjust this as needed
        }
    }

    override suspend fun connectToWebSocket(username: String): Boolean {
        return try {
            socketSession = client.webSocketSession {
                url("${RealtimeTicTacToeMessagingClient.BASE_URL}${RealtimeTicTacToeMessagingClient.TicTacToe_SOCKET_ENDPOINT}?username=$username")
            }
            socketSession?.isActive == true
        } catch (e: Exception) {
            return false
        }

    }

    override fun getGameStateStream(): Flow<GameState> {
        return try {
            socketSession?.let { socketSession ->
                socketSession.incoming
                    .receiveAsFlow()
                    .filterIsInstance<Frame.Text>()
                    .map { Json.decodeFromString<GameStateDto>(it.readText()).toGameState() }
            } ?: flow { }
        } catch (e: Exception) {
            println("Error in getGameStateStream : ${e.message}")
            e.printStackTrace()
            flow { emit(GameState()) }
        }
    }

    override suspend fun sendPlayerTurn(action: PlayerTurn) {
        try {
            socketSession?.send(Frame.Text(Json.encodeToString(action)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun disconnect() {
        println("Disconnecting")
        socketSession?.close()
        socketSession = null
        println("Disconnected")

    }
}