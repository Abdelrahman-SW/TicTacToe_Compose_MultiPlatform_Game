package com.beapps.tictactoe_game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.beapps.tictactoe_game.domain.models.GameState
import com.beapps.tictactoe_game.presentation.components.TicTacToeBoard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    TicTacToeBoard(
        modifier = Modifier.size(300.dp),
        gameState = GameState(board = arrayOf(
            arrayOf('X', null, null),
            arrayOf(null, 'O', 'O'),
            arrayOf(null, 'X', null),
        )),
        xPlayerColor = Color.Green,
        oPlayerColor = Color.Blue,
        onTurn = {_ , _ ->},
    )
}