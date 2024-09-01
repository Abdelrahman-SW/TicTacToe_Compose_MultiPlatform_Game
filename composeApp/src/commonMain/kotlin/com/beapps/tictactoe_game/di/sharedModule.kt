package com.beapps.tictactoe_game.di

import com.beapps.tictactoe_game.data.RealtimeTicTacToeMessagingClientKtorImpl
import com.beapps.tictactoe_game.domain.RealtimeTicTacToeMessagingClient
import com.beapps.tictactoe_game.presentation.TicTacToeSharedViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    viewModelOf(::TicTacToeSharedViewModel)
    singleOf(::RealtimeTicTacToeMessagingClientKtorImpl).bind<RealtimeTicTacToeMessagingClient>()
    single {
        HttpClient(CIO) {
            install(Logging)
            install(WebSockets)
        }
    }
}