package dev.geanbrandao.minhasdespesas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.navigation.bottomnavigationbar.NavBar
import dev.geanbrandao.minhasdespesas.navigation.domain.Screen
import dev.geanbrandao.minhasdespesas.navigation.graph.NavGraph
import dev.geanbrandao.minhasdespesas.navigation.graph.NavigationGraph
import dev.geanbrandao.minhasdespesas.ui.theme.AppTheme

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {


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
                            NavBar(navHostController = navHostController)
                        }
                    },
                ) { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                    ) {
//                        NavGraph(navHostController = navHostController)
                        NavigationGraph(navHostController = navHostController)
                    }
                }
            }
        }
    }
}

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
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                NavGraph(navHostController = navHostController)
            }
        }
    }
}

// todo campo description está com caps words
