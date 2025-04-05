package com.example.weathertracker.shared.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.AttributeKey
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

internal val httpClientModule: Module = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    }
                )
            }
            install(Logging) {
                level = LogLevel.BODY
            }
            defaultRequest {
                // url("https://api.openweathermap.org/data/2.5")
            }
        }
    }
}