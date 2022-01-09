package dev.geanbrandao.minhasdespesas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.feature.navigation.presentation.components.NavBar
import dev.geanbrandao.minhasdespesas.feature.navigation.presentation.components.NavGraph
import dev.geanbrandao.minhasdespesas.feature.navigation.utils.Screen
import dev.geanbrandao.minhasdespesas.ui.theme.AppTheme

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                val navHostController = rememberNavController()
                val visibilityState = remember {
                    mutableStateOf(false)
                }
                navHostController.addOnDestinationChangedListener { _, destination, _ ->
                    visibilityState.value = when (destination.route) {
                        Screen.Expenses.route -> {
                            true
                        }
                        Screen.Add.route -> {
                            true
                        }
                        Screen.Profile.route -> {
                            true
                        }
                        Screen.Charts.route -> {
                            true
                        }
                        else -> false
                    }
                }
                Scaffold(
                    bottomBar = {
                        AnimatedVisibility(
                            visibilityState.value,
                            enter = expandVertically(),
                            exit = shrinkVertically(),
                        ) {
                            NavBar(
                                navHostController = navHostController,
                            )
                        }
                    },
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        NavGraph(navHostController = navHostController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        val navHostController = rememberNavController()
        val visibilityState = remember {
            mutableStateOf(false)
        }
        navHostController.addOnDestinationChangedListener { _, destination, _ ->
            visibilityState.value = when (destination.route) {
                Screen.Expenses.route -> {
                    true
                }
                Screen.Add.route -> {
                    true
                }
                Screen.Profile.route -> {
                    true
                }
                else -> false
            }
        }
        Scaffold(
            bottomBar = {
                NavBar(
                    navHostController = navHostController,
                )
            },
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                NavGraph(navHostController = navHostController)
            }
        }
    }
}
