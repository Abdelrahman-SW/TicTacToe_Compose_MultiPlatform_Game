package com.beapps.tictactoe_game


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.beapps.tictactoe_game.presentation.GameScreen
import com.beapps.tictactoe_game.presentation.HomeScreen
import com.beapps.tictactoe_game.presentation.TicTacToeViewModel
import com.beapps.tictactoe_game.presentation.util.GameScreen
import com.beapps.tictactoe_game.presentation.util.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    KoinContext {
        val ticTacToeViewModel = koinViewModel<TicTacToeViewModel>()
        val navController = rememberNavController()
        NavHost(navController , startDestination = HomeScreen) {
            composable<HomeScreen> {
                HomeScreen(navController = navController , viewModel = ticTacToeViewModel)
            }
            composable<GameScreen> {
                val gameScreen = it.toRoute<GameScreen>()
                GameScreen(
                    navController = navController,
                    viewModel = ticTacToeViewModel,
                    username = gameScreen.username
                )
            }
        }
    }
}