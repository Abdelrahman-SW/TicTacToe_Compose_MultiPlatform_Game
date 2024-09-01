package com.beapps.tictactoe_game.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import tictactoegame.composeapp.generated.resources.Res
import tictactoegame.composeapp.generated.resources.chango_regular

val darkGray = Color(0xFF292A2E)
val lightBlue100 = Color(0xFF0088cc)
val lightBlue10035Alpha = Color(0x990088CC)
val mainComponentColor = Color(0xFF202020)
val mainComponentColor35Alpha = Color(0x80222222)


@Composable
fun GetChangoFontFamily() = FontFamily(
    Font(Res.font.chango_regular , FontWeight.Normal),
)