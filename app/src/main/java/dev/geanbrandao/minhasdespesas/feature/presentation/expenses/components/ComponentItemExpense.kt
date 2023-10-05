package dev.geanbrandao.minhasdespesas.feature.presentation.expenses.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.dividers.ItemDefaultDivider
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerTwo
import dev.geanbrandao.minhasdespesas.common.components.texts.TextBodyMediumSingleLine
import dev.geanbrandao.minhasdespesas.common.components.texts.TextLabelMedium
import dev.geanbrandao.minhasdespesas.common.components.texts.TextTitleLarge
import dev.geanbrandao.minhasdespesas.common.utils.extensions.getDayNumber
import dev.geanbrandao.minhasdespesas.common.utils.extensions.getMonth3LettersName
//import dev.geanbrandao.minhasdespesas.core.database.db.ExpenseDb
import dev.geanbrandao.minhasdespesas.feature.presentation.expenses.util.TestTags.ITEM_EXPENSE_ROOT
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingHalf
import java.time.OffsetDateTime

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ItemExpenseWithSwipe(
//    expenseDb: ExpenseDb,
//    isLastItem: Boolean,
//    onSwipeToDelete: (id: Long) -> Unit,
//    onSwipeToEdit: (id: Long) -> Unit,
//) {
//    val swipeState = rememberDismissState(
//        confirmValueChange = { dismissValue ->
//            if (dismissValue == DismissValue.DismissedToEnd) {
//                onSwipeToDelete(expenseDb.expenseId)
//            } else if (dismissValue == DismissValue.DismissedToStart) {
//                onSwipeToEdit(expenseDb.expenseId)
//            }
//            true
//        }
//    )
//    SwipeToDismiss(
//        state = swipeState,
//        background = {
//            if (swipeState.dismissDirection == DismissDirection.StartToEnd) {
//                BgSwipeToDelete()
//            }
//            if (swipeState.dismissDirection == DismissDirection.EndToStart) {
//                BgSwipeToEdit()
//            }
//        },
//        dismissContent = {
//            Column(
//                modifier = Modifier
//                    .background(color = MaterialTheme.colorScheme.background)
//            ) {
//                ItemExpense(expenseDb)
//                SpacerTwo()
//                ItemDefaultDivider(
//                    color = MaterialTheme.colorScheme.onBackground,
//                    shouldShow = isLastItem.not(),
//                    modifier = Modifier.padding(horizontal = PaddingDefault)
//                )
//            }
//        },
//    )
//}
//
//@Composable
//private fun ItemExpense(expenseDb: ExpenseDb) {
//    Column(
//        modifier = Modifier
//            .testTag(tag = ITEM_EXPENSE_ROOT)
//            .padding(
//                start = PaddingDefault,
//                end = PaddingDefault,
//                top = PaddingHalf,
//            )
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Column {
//                TextTitleLarge(
//                    expenseDb.selectedDate.getDayNumber()
//                )
//                TextLabelMedium(
//                    text = expenseDb.selectedDate.getMonth3LettersName()
//                )
//            }
//            SpacerTwo()
//            TextBodyMediumSingleLine(
//                text = expenseDb.name,
//                modifier = Modifier.weight(weight = 1f)
//            )
//            SpacerTwo()
//            TextTitleLarge(
//                text = stringResource(
//                    id = R.string.text_default_value_cipher,
//                    expenseDb.amount
//                )
//            )
//        }
//    }
//}
//
//@Preview
//@Composable
//fun ItemExpensePreview() {
//    val expenseDb = ExpenseDb(
//        expenseId = 1L,
//        name = "Expense",
//        amount = 9.99f,
//        description = "One expense of this week",
//        selectedDate = OffsetDateTime.now(),
//        createdAt = OffsetDateTime.now(),
//        updatedAt = OffsetDateTime.now(),
//    )
//    ItemExpense(expenseDb = expenseDb)
//}
//
//@Preview("ItemExpenseWithSwipe preview")
//@Composable
//fun ItemExpenseWithSwipePreview() {
//    val expenseDb = ExpenseDb(
//        expenseId = 1L,
//        name = "Expense",
//        amount = 9.99f,
//        description = "One expense of this week",
//        selectedDate = OffsetDateTime.now(),
//        createdAt = OffsetDateTime.now(),
//        updatedAt = OffsetDateTime.now(),
//    )
//    ItemExpenseWithSwipe(
//        expenseDb = expenseDb,
//        isLastItem = false,
//        onSwipeToDelete = { },
//        onSwipeToEdit = { },
//    )
//}
