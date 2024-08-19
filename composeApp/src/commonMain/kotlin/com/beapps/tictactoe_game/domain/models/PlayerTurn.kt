package com.beapps.tictactoe_game.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class PlayerTurn(val x : Int , val y : Int)
