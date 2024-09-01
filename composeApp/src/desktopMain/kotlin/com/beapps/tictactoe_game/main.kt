package com.beapps.tictactoe_game

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.beapps.tictactoe_game.di.initKoin
import com.beapps.tictactoe_game.presentation.App

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "TicTacToeGame",
        ) {
            App()
        }
    }
}