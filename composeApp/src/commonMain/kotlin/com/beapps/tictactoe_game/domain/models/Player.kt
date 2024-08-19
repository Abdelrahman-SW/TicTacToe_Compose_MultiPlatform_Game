package com.beapps.tictactoe_game.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class Player(
    val username : String,
    var type : PlayerType? = null
)

@Serializable
enum class PlayerType (val char: Char) {
    X('X') , O('O');

}
