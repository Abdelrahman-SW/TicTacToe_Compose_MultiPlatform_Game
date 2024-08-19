package com.beapps.tictactoe_game.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.beapps.tictactoe_game.domain.models.GameState

@Composable
fun TicTacToeBoard(
    modifier: Modifier = Modifier,
    gameState: GameState,
    xPlayerColor: Color,
    oPlayerColor: Color,
    linesColor: Color = Color.Black,
    fieldSize: DpSize = DpSize(50.dp, 50.dp),
    fieldStrokeWidth: Dp = 3.dp,
    lineStrokeWidth: Dp = 3.dp,
    onTurn: (Int, Int) -> Unit
) {

    Canvas(modifier = modifier
        .pointerInput(true) {
            detectTapGestures { offest ->
                val x = (3 * offest.x.toInt() / size.width)
                val y = (3 * offest.y.toInt() / size.height)
                onTurn(x, y)
            }
        }
    ) {
        drawBoardLines(color = linesColor , lineStrokeWidth)
        gameState.board.forEachIndexed { y, _ ->
            gameState.board[y].forEachIndexed { x, char ->
                val offset = Offset(
                    x = x * size.width * (1 / 3f) + size.width / 6f,
                    y = y * size.height * (1 / 3f) + size.height / 6f
                )
                if (char == 'X') {
                    drawX(xPlayerColor, offset, fieldSize.toSize() , fieldStrokeWidth)
                } else if (char == 'O') {
                    drawO(oPlayerColor, offset, fieldSize.toSize() , fieldStrokeWidth)
                }
            }
        }

    }
}

private fun DrawScope.drawO(
    color: Color,
    center: Offset,
    size: Size,
    strokeWidth: Dp
) {
    drawCircle(
        color = color,
        center = center,
        radius = size.width / 2f,
        style = Stroke(
            width = strokeWidth.toPx(),
            cap = StrokeCap.Round
        )
    )
}

private fun DrawScope.drawX(
    color: Color,
    center: Offset,
    size: Size ,
    strokeWidth: Dp
) {
    drawLine(
        color = color,
        start = Offset(
            x = center.x - size.width / 2f,
            y = center.y - size.height / 2f
        ),
        end = Offset(
            x = center.x + size.width / 2f,
            y = center.y + size.height / 2f
        ),
        strokeWidth = strokeWidth.toPx(),
        cap = StrokeCap.Round
    )
    drawLine(
        color = color,
        start = Offset(
            x = center.x - size.width / 2f,
            y = center.y + size.height / 2f
        ),
        end = Offset(
            x = center.x + size.width / 2f,
            y = center.y - size.height / 2f
        ),
        strokeWidth = strokeWidth.toPx(),
        cap = StrokeCap.Round
    )
}


private fun DrawScope.drawBoardLines(color: Color , strokeWidth : Dp) {
    // 1st vertical line
    drawLine(
        color = color,
        start = Offset(
            x = size.width * (1 / 3f),
            y = 0f
        ),
        end = Offset(
            x = size.width * (1 / 3f),
            y = size.height
        ),
        strokeWidth = strokeWidth.toPx(),
        cap = StrokeCap.Round
    )

    // 2nd vertical line
    drawLine(
        color = color,
        start = Offset(
            x = size.width * (2 / 3f),
            y = 0f
        ),
        end = Offset(
            x = size.width * (2 / 3f),
            y = size.height
        ),
        strokeWidth = strokeWidth.toPx(),
        cap = StrokeCap.Round
    )

    // 1st horizontal line
    drawLine(
        color = color,
        start = Offset(
            x = 0f,
            y = size.height * (1 / 3f)
        ),
        end = Offset(
            x = size.width,
            y = size.height * (1 / 3f)
        ),
        strokeWidth = strokeWidth.toPx(),
        cap = StrokeCap.Round
    )

    // 2nd horizontal line
    drawLine(
        color = color,
        start = Offset(
            x = 0f,
            y = size.height * (2 / 3f)
        ),
        end = Offset(
            x = size.width,
            y = size.height * (2 / 3f)
        ),
        strokeWidth = strokeWidth.toPx(),
        cap = StrokeCap.Round
    )
}


fun DpSize.toSize(): Size {
    return Size(width.value, height.value)
}