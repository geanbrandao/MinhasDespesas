package br.dev.geanbrandao.common.presentation.components.datepicker

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import br.dev.geanbrandao.common.domain.isNotNull
import br.dev.geanbrandao.common.presentation.components.button.ButtonAction
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerView( // todo arrumar title e subtitle
    isVisible: Boolean,
    warningMessage: String,
    confirmButtonText: String,
    dismissButtonText: String,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSelectRangeDate: (startDate: Long, endDate: Long) -> Unit,
) {
    val dateRangePickerState = rememberDateRangePickerState()
    val context = LocalContext.current

    if (isVisible) {
        Column(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {
            DateRangePicker(
                state = dateRangePickerState,
                showModeToggle = false,
                modifier = Modifier.weight(1f),
            )
            Row {
                Spacer(modifier = Modifier.weight(1f))
                ButtonAction(
                    text = dismissButtonText,
                    isEnabled = true,
                    onClick = onDismiss,
                )
                ButtonAction(
                    text = confirmButtonText,
                    isEnabled = true,
                    onClick = {
                        val startDate = dateRangePickerState.selectedStartDateMillis
                        val endDate = dateRangePickerState.selectedEndDateMillis
                        if (startDate.isNotNull() and endDate.isNotNull()) {
                            onSelectRangeDate(startDate!!, endDate!!)
                            onDismiss()
                        } else {
                            Toast.makeText(context, warningMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.size(PaddingTwo))
        }
    }
}


@Preview
@Composable
private fun DateRangePickerPreview() {
    DateRangePickerView(
        isVisible = true,
        warningMessage = "warningMessage",
        confirmButtonText = "OK",
        dismissButtonText = "Dismiss",
        onDismiss = {},
        onSelectRangeDate = { l: Long, l1: Long -> },
    )
}
