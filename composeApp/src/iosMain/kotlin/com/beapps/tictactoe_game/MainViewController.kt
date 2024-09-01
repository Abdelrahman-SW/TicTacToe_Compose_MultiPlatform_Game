package com.beapps.tictactoe_game

import androidx.compose.ui.window.ComposeUIViewController
import com.beapps.tictactoe_game.di.initKoin
import com.beapps.tictactoe_game.presentation.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}