package com.beapps.tictactoe_game

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.beapps.tictactoe_game.domain.models.GameState
import com.beapps.tictactoe_game.presentation.App
import com.beapps.tictactoe_game.presentation.components.TicTacToeBoard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowCompat.setDecorFitsSystemWindows(window, false)
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