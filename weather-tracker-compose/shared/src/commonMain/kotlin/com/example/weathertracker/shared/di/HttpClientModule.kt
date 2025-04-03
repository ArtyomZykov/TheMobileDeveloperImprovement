package com.example.weathertracker.shared.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.defaultRequest
import io.ktor.util.AttributeKey
import org.koin.core.module.Module
import org.koin.dsl.module

internal val httpClientModule: Module = module {
    single {
        HttpClient(CIO) {
            defaultRequest {
                setAttributes {
                    put(AttributeKey("appid"), "5840b079927564b5d6ee2822db144db8")
                }
                url("https://api.openweathermap.org/data/2.5/")
            }
        }
    }
}