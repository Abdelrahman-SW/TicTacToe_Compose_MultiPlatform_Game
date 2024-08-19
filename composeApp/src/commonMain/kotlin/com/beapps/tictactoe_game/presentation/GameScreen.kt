package com.beapps.tictactoe_game.presentation

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.beapps.tictactoe_game.presentation.util.GameScreen

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TicTacToeViewModel,
    username: String
) {
    val gameState = viewModel.gameState
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        val maxWidth = constraints.maxWidth
        val maxHeight = constraints.maxHeight

        val boardWidth = with(LocalDensity.current) {
            (maxWidth.toFloat()/1.3f).toDp()
        }


        Column (modifier = Modifier.fillMaxSize() , horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.Center) {
            val stateTxt = if (gameState.connectedPlayers.size < 2) "Waiting for the Second player . . ."
            else if (gameState.winingPlayer != null) "${gameState.winingPlayer.username} is Won !"
            else if (gameState.isBoardFull) "Game is Draw"
            else "It's ${gameState.playerAtTurn?.username ?: "Guest"}'s turn"

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Your Name : $username",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildAnnotatedString {
                    append("You Are The: ")
                    val char = gameState.connectedPlayers.find { it.username == username }?.type?.char ?: ""
                    pushStyle(SpanStyle(color = if ( char == 'X') Color.Green else Color.Blue))
                    append(char.toString() ?: "")
                },
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stateTxt,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))


            TicTacToeBoard(
                modifier = Modifier
                    .width(boardWidth)
                    .aspectRatio(1f)
                    .padding(16.dp),
                gameState = gameState,
                xPlayerColor = Color.Green,
                oPlayerColor = Color.Blue,
                linesColor = Color.Black,
                fieldSize = DpSize(boardWidth / 6, boardWidth / 6),
                fieldStrokeWidth = 3.dp,
                lineStrokeWidth = 3.dp,
                onTurn = viewModel::onMakeTurn
            )

        }
    }
}