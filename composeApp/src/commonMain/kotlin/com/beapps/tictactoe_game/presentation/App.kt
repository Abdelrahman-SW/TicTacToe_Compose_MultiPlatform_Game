package com.beapps.tictactoe_game.presentation


import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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
        val ticTacToeSharedViewModel = koinViewModel<TicTacToeSharedViewModel>()
        val navController = rememberNavController()
        NavHost(navController , startDestination = HomeScreen) {
            composable<HomeScreen> {
                HomeScreen(navController = navController , viewModel = ticTacToeSharedViewModel)
            }
            composable<GameScreen> {
                GameScreen(
                    navController = navController,
                    viewModel = ticTacToeSharedViewModel,
                )
            }
        }
    }
}