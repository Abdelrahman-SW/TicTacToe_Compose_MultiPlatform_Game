package com.beapps.tictactoe_game.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beapps.tictactoe_game.presentation.util.GameScreen
import com.beapps.tictactoe_game.presentation.util.HomeScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TicTacToeViewModel
) {
    val username = viewModel.usernameTextFieldState

    LaunchedEffect(true) {
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                TicTacToeViewModel.UIEvents.GoToGameScreen -> {
                    navController.navigate(GameScreen(viewModel.usernameTextFieldState))
                }
                is TicTacToeViewModel.UIEvents.ShowErrorMessage -> {
                    println("Error Message: ${event.message}")
                }
            }
        }
    }

    Column(modifier = modifier.fillMaxSize() , verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = username , onValueChange = viewModel::onUsernameChange , placeholder = {
            Text(text = "Enter Your Name")
        })

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            viewModel.connectToGame()
        }){
            Text(text = "Join Game")
        }

        if (viewModel.isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }


    }
}