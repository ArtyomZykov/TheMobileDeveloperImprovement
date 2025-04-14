package com.example.weathertracker.shared.di

import androidx.compose.ui.window.ComposeUIViewController
import com.example.weathertracker.shared.ComposeApp

fun MainViewController() = ComposeUIViewController(
    configure = { enforceStrictPlistSanityCheck = false },
) {
    ComposeApp()
}
