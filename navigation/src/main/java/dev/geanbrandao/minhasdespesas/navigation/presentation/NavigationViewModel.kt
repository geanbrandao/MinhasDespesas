package dev.geanbrandao.minhasdespesas.navigation.presentation

import androidx.lifecycle.ViewModel
import dev.geanbrandao.minhasdespesas.navigation.data.AppNavigator
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NavigationViewModel(
    private val appNavigator: AppNavigator,
): ViewModel() {
    val navigationChannel = appNavigator.navigationChannel
}