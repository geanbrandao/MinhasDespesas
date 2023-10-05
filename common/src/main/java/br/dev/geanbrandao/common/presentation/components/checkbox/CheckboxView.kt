package br.dev.geanbrandao.common.presentation.components.checkbox

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckboxView(
    isChecked: Boolean = false,
    onCheckChangeListener: (isChecked: Boolean) -> Unit,
) {
    Checkbox(
        checked = isChecked,
        onCheckedChange = onCheckChangeListener
    )
}

@Preview
@Composable
private fun CheckboxViewPreview() {
    CheckboxView {

    }
}