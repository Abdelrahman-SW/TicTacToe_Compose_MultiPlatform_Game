package com.beapps.tictactoe_game.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.beapps.tictactoe_game.presentation.components.TicTacToeBoard
import com.beapps.tictactoe_game.presentation.util.GetChangoFontFamily
import com.beapps.tictactoe_game.presentation.util.darkGray
import com.beapps.tictactoe_game.presentation.util.lightBlue100
import com.beapps.tictactoe_game.presentation.util.mainComponentColor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TicTacToeSharedViewModel,
) {
    val username = viewModel.usernameTextFieldState
    val gameState = viewModel.gameState
    val scope = rememberCoroutineScope()
    LaunchedEffect(true) {
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                TicTacToeSharedViewModel.UIEvents.GoToGameScreen -> Unit

                is TicTacToeSharedViewModel.UIEvents.ShowErrorMessage -> {
                    println("Error Message: ${event.message}")
                    // do platform show error pop up implementation
                }

                TicTacToeSharedViewModel.UIEvents.ExitGame -> navController.navigateUp()
            }
        }
    }

    BoxWithConstraints(
        modifier = modifier.fillMaxSize().background(darkGray).padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        val maxWidth = constraints.maxWidth
        val maxHeight = constraints.maxHeight

        val boardWidth = with(LocalDensity.current) {
            (maxWidth.toFloat() / 1.3f).toDp()
        }
        val boardHeight = with(LocalDensity.current) {
            (maxHeight.toFloat() / 1.3f).toDp()
        }

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (gameState.connectedPlayers.size == 2) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = buildAnnotatedString {
                        append(gameState.connectedPlayers[0].username)
                        pushStyle(
                            SpanStyle(
                                color = lightBlue100,
                                fontSize = 24.sp,
                                fontFamily = GetChangoFontFamily()
                            )
                        )
                        append(" V.s ")
                        pop()
                        append(gameState.connectedPlayers[1].username)
                    },
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = GetChangoFontFamily()
                )

                Spacer(modifier = Modifier.height(32.dp))

            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Your Name : $username",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                fontFamily = GetChangoFontFamily()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildAnnotatedString {
                    append("You Are The: ")
                    val char =
                        gameState.connectedPlayers.find { it.username == username }?.type?.char
                            ?: ""
                    pushStyle(SpanStyle(color = if (char == 'X') Color.White else lightBlue100))
                    append(char.toString() ?: "")
                },
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                fontFamily = GetChangoFontFamily()
            )

            Spacer(modifier = Modifier.height(32.dp))

            val stateTxt =
                if (gameState.connectedPlayers.size < 2) "Waiting for the Second player . . ."
                else if (gameState.winingPlayer != null) "${gameState.winingPlayer.username} is Won !"
                else if (gameState.isBoardFull) "Game is Draw"
                else "It's ${gameState.playerAtTurn?.username ?: "Guest"}'s turn"

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stateTxt,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontFamily = GetChangoFontFamily()
            )

            Spacer(modifier = Modifier.height(32.dp))


            TicTacToeBoard(
                modifier = Modifier
                    .width(boardWidth)
                    .aspectRatio(1f)
                    .padding(16.dp),
                gameState = gameState,
                xPlayerColor = Color.White,
                oPlayerColor = lightBlue100,
                linesColor = mainComponentColor,
                fieldSize = DpSize(boardWidth / 6, boardWidth / 6),
                fieldStrokeWidth = 3.dp,
                lineStrokeWidth = 3.dp,
                onTurn = viewModel::onMakeTurn
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (gameState.countdown != 0) {
                scope.launch { scrollState.scrollTo(scrollState.maxValue) }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "The New Round Will Be Started At ${gameState.countdown} Seconds . . .",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = GetChangoFontFamily()
                )
            }
        }

        IconButton(onClick = { navController.navigateUp() }, modifier = Modifier.align(
            Alignment.TopStart
        ).size(32.dp)) {
            Icon(
                Icons.Default.ArrowBack, "Back", tint = Color.White
            )
        }
    }
}