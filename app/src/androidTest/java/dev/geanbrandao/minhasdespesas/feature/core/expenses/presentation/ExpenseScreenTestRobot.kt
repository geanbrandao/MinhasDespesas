package dev.geanbrandao.minhasdespesas.feature.core.expenses.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.printToLog
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dev.geanbrandao.minhasdespesas.MainActivity
import dev.geanbrandao.minhasdespesas.feature.core.expenses.util.TestTags.COMPONENT_SCREEN_WARNING
import dev.geanbrandao.minhasdespesas.feature.core.expenses.util.TestTags.ITEM_EXPENSE_ROOT

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
class ExpenseScreenTestRobot(
    private val composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {

    fun removeItemExpense() = apply {
        composeRule
            .onAllNodesWithTag(ITEM_EXPENSE_ROOT)
            .onFirst()
            .performTouchInput {
                swipeRight()
            }
            .printToLog("DEBUG1")
    }

    fun checkScreenWarningWhenFilterAreSelected() = apply {
        composeRule
            .onNodeWithTag(COMPONENT_SCREEN_WARNING)
            .assertIsDisplayed()
            .assert(hasAnyDescendant(hasText("Ops, nada por aqui")))
            .assert(hasAnyDescendant(hasText("Nenhuma despesa a ser listada de acordo " +
                    "com o(s) filtro(s) selecionados")))
    }
}
