package com.beapps.tictactoe_game.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.beapps.tictactoe_game.presentation.util.GameScreen
import com.beapps.tictactoe_game.presentation.util.GetChangoFontFamily
import com.beapps.tictactoe_game.presentation.util.darkGray
import com.beapps.tictactoe_game.presentation.util.lightBlue100
import com.beapps.tictactoe_game.presentation.util.mainComponentColor35Alpha
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import tictactoegame.composeapp.generated.resources.Res
import tictactoegame.composeapp.generated.resources.game_1
import tictactoegame.composeapp.generated.resources.gamepad

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TicTacToeSharedViewModel
) {
    val username = viewModel.usernameTextFieldState

    LaunchedEffect(true) {
        // when u back from game screen to home screen
        viewModel.disconnectAnyActiveSession()
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                TicTacToeSharedViewModel.UIEvents.GoToGameScreen -> {
                    navController.navigate(GameScreen)
                }

                is TicTacToeSharedViewModel.UIEvents.ShowErrorMessage -> {
                    println("Error Message: ${event.message}")
                    // do platform show error pop up implementation
                }

                TicTacToeSharedViewModel.UIEvents.ExitGame -> Unit
            }
        }
    }

    Box(modifier = modifier.fillMaxSize().background(darkGray)) {

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(Res.drawable.game_1),
                contentDescription = "Game Logo",
                modifier = Modifier.size(150.dp)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = lightBlue100,
                        unfocusedIndicatorColor = lightBlue100,
                        disabledIndicatorColor = lightBlue100,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = mainComponentColor35Alpha,
                        unfocusedContainerColor = mainComponentColor35Alpha,
                        disabledContainerColor = mainComponentColor35Alpha,
                        cursorColor = Color.White
                    ),
                    value = username,
                    onValueChange = viewModel::onUsernameChange,
                    label = {
                        Text(
                            text = "Enter Your Name",
                            color = Color.White,
                            fontFamily = GetChangoFontFamily()
                        )
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(colors = ButtonDefaults.buttonColors(containerColor = lightBlue100),
                    onClick = {
                        viewModel.connectToGame()
                    }) {
                    Text(text = "Join Game", color = Color.White, fontFamily = GetChangoFontFamily())
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Image(
                    painter = painterResource(Res.drawable.gamepad),
                    contentDescription = "Game Logo2",
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    fontSize = 10.sp,
                    modifier = Modifier.fillMaxWidth(),
                    text = "Simple TicTacToe Game Developed By Abdelrahman Gadelrab @2024",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = GetChangoFontFamily()
                )

            }
        }
        if (viewModel.isLoading) {
            CircularProgressIndicator(
                color = lightBlue100,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}