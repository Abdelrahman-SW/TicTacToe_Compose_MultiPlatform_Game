package com.beapps.tictactoe_game.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.tictactoe_game.domain.RealtimeTicTacToeMessagingClient
import com.beapps.tictactoe_game.domain.models.GameState
import com.beapps.tictactoe_game.domain.models.PlayerTurn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TicTacToeSharedViewModel(
    private val ticTacToeClient: RealtimeTicTacToeMessagingClient
) : ViewModel() {

    var usernameTextFieldState by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var gameState by mutableStateOf(GameState())
        private set

    private val _uiEvents = MutableSharedFlow<UIEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun onUsernameChange(username: String) {
        usernameTextFieldState = username
    }


    fun connectToGame() {
        if (usernameTextFieldState.isBlank()) {
            viewModelScope.launch {
                _uiEvents.emit(UIEvents.ShowErrorMessage("Username can't be empty"))
                return@launch
            }
            return
        }
        viewModelScope.launch {
            isLoading = true
            val isConnected = ticTacToeClient.connectToWebSocket(usernameTextFieldState)
            isLoading = false
            if (isConnected) {
                _uiEvents.emit(UIEvents.GoToGameScreen)
                ticTacToeClient.getGameStateStream().collect { state ->
                    gameState = state
                }
                ticTacToeClient.getSessionIsActiveState().collect { isActive ->
                    if (!isActive) {
                         disconnectAnyActiveSession()
                        _uiEvents.emit(UIEvents.ExitGame)
                    }
                }
            }
            else {
                // show error
                _uiEvents.emit(UIEvents.ShowErrorMessage("Couldn't connect to the server"))
            }
        }
    }

    fun disconnectAnyActiveSession(dispatcher: CoroutineContext = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            ticTacToeClient.disconnect()
        }
    }

    fun onMakeTurn(x: Int, y: Int) {
        viewModelScope.launch {
            // do some validation :
            if (gameState.board[y][x] != null || gameState.winingPlayer != null || gameState.playerAtTurn != gameState.connectedPlayers.find { it.username == usernameTextFieldState }) return@launch
            ticTacToeClient.sendPlayerTurn(PlayerTurn(x, y))
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnectAnyActiveSession(NonCancellable)
    }

    sealed class UIEvents {
        data class ShowErrorMessage(val message: String) : UIEvents()
        data object GoToGameScreen : UIEvents()
        data object ExitGame : UIEvents()
    }
}

