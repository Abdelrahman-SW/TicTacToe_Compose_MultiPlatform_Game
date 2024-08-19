package com.beapps.tictactoe_game.presentation.util

import kotlinx.serialization.Serializable




@Serializable
data object HomeScreen


@Serializable
data class GameScreen (val username : String)