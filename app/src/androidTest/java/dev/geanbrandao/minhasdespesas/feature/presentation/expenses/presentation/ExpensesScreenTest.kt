package dev.geanbrandao.minhasdespesas.feature.presentation.expenses.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAncestors
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeRight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.MainActivity
import dev.geanbrandao.minhasdespesas.feature.presentation.expenses.ExpensesScreen
import dev.geanbrandao.minhasdespesas.feature.presentation.expenses.util.TestTags.COMPONENT_SCREEN_WARNING
import dev.geanbrandao.minhasdespesas.feature.presentation.expenses.util.TestTags.ITEM_EXPENSE_ROOT
import dev.geanbrandao.minhasdespesas.feature.presentation.expenses.util.TestTags.ITEM_FILTER_ICON_CLOSE
import dev.geanbrandao.minhasdespesas.feature.presentation.expenses.util.TestTags.ITEM_FILTER_ROOT
import dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils.Screen
import dev.geanbrandao.minhasdespesas.ui.theme.AppTheme
import dev.geanbrandao.minhasdespesas.ui.theme.NavBarHeightSize
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalAnimationApi
@OptIn(ExperimentalMaterial3Api::class)
class ExpensesScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeRule.setContent {
            val navController = rememberNavController()
            AppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Expenses.route
                ) {
                    composable(route = Screen.Expenses.route) {
                        ExpensesScreen(
                            modifier = Modifier.padding(bottom = NavBarHeightSize),
                            navHostController = navController,
                        )
                    }
                }
            }
        }
    }

    @Test
    fun checkIfItemExpenseIsDisplayed() {
        composeRule
            .onAllNodesWithTag(ITEM_EXPENSE_ROOT)
            .assertCountEquals(3)
    }

    @Test
    fun checkIfAllItemFilterIsDisplayed() {
        composeRule
            .onAllNodesWithTag(ITEM_FILTER_ROOT)
            .assertCountEquals(5)
    }

    @Test
    fun checkRemoveOneFilterIsWorking() {
        composeRule
            .onAllNodesWithTag(ITEM_FILTER_ROOT, useUnmergedTree = true)
            .filterToOne(
                matcher = hasAnyDescendant(matcher = hasText(text = "Filtro 3")) and
                        hasAnyDescendant(matcher = hasTestTag(testTag = ITEM_FILTER_ICON_CLOSE))
            )
            .onChildren()
            .filterToOne(matcher = hasTestTag(testTag = ITEM_FILTER_ICON_CLOSE))
            .performClick()

        composeRule
            .onAllNodesWithTag(testTag = ITEM_FILTER_ROOT, useUnmergedTree = true)
            .assertCountEquals(expectedSize = 4)
    }

    @ExperimentalTestApi
    @Test
    fun checkSwipeToDeleteRemoveItemFromTheScreen() {
        composeRule
            .onAllNodesWithTag(ITEM_EXPENSE_ROOT)
            .assertCountEquals(3)
            .onFirst()
            .performTouchInput {
                swipeRight()
            }
            .onAncestors()
            .assertCountEquals(2)
    }

    @Test
    fun checkIfCorrectWarningTitleAndMessageIsDisplay() {
        composeRule
            .onAllNodesWithTag(ITEM_EXPENSE_ROOT)
            .onFirst()
            .performTouchInput {
                swipeRight()
            }

        composeRule
            .onAllNodesWithTag(ITEM_EXPENSE_ROOT)
            .onFirst()
            .performTouchInput {
                swipeRight()
            }

        composeRule
            .onAllNodesWithTag(ITEM_EXPENSE_ROOT)
            .onFirst()
            .performTouchInput {
                swipeRight()
            }

        composeRule
            .onNodeWithTag(COMPONENT_SCREEN_WARNING)
            .assertIsDisplayed()
            .assert(hasAnyDescendant(hasText("Ops, nada por aqui")))
            .assert(hasAnyDescendant(hasText("Nenhuma despesa a ser listada de acordo " +
                    "com o(s) filtro(s) selecionados")))
    }
}
