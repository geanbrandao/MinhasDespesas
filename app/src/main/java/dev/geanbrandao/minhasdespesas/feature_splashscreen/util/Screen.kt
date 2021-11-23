package dev.geanbrandao.minhasdespesas.feature_splashscreen.util

sealed class Screen(val route: String) {
    object Splashscreen: Screen("splashscreen")
}
