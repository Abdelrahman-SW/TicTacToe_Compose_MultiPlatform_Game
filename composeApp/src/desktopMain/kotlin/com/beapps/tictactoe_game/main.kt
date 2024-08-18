package com.beapps.tictactoe_game

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TicTacToeGame",
    ) {
        App()
    }
}