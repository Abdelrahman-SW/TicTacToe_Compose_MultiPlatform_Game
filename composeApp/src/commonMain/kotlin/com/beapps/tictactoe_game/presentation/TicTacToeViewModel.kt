package com.beapps.tictactoe_game.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.tictactoe_game.domain.RealtimeTicTacToeMessagingClient
import com.beapps.tictactoe_game.domain.models.GameState
import com.beapps.tictactoe_game.domain.models.PlayerTurn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TicTacToeViewModel(
    private val ticTacToeClient: RealtimeTicTacToeMessagingClient
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var gameState by mutableStateOf(GameState())
        private set

    private val _uiEvents = MutableSharedFlow<UIEvents>()
    val uiEvents = _uiEvents.asSharedFlow()


    fun connectToGame(username: String) {
        viewModelScope.launch {
            isLoading = true
            val isConnected = ticTacToeClient.connectToWebSocket(username)
            isLoading = false
            if (isConnected) {
                _uiEvents.emit(UIEvents.GoToGameScreen)
                ticTacToeClient.getGameStateStream().collect { state ->
                    gameState = state
                }
            }
            else {
                // show error
                _uiEvents.emit(UIEvents.ShowErrorMessage("Couldn't connect to the server"))
            }
        }
    }

    fun onMakeTurn(x: Int, y: Int) {
        viewModelScope.launch {
            // do some validation :
            if (gameState.board[y][x] != null || gameState.winingPlayer != null) return@launch
            ticTacToeClient.sendPlayerTurn(PlayerTurn(x, y))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            ticTacToeClient.disconnect()
        }
    }

    sealed class UIEvents {
        data class ShowErrorMessage(val message: String) : UIEvents()
        data object GoToGameScreen : UIEvents()
    }
}

