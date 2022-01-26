package dev.geanbrandao.minhasdespesas.feature.add.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.AppToolbar
import dev.geanbrandao.minhasdespesas.common.components.InputField
import dev.geanbrandao.minhasdespesas.ui.theme.AppTypography
import dev.geanbrandao.minhasdespesas.ui.theme.MarginDefault
import dev.geanbrandao.minhasdespesas.ui.theme.MarginHalf
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault
import dev.geanbrandao.minhasdespesas.ui.theme.SizeIconLarge

@Composable
fun AddScreen(
    navHostController: NavHostController
) {
    val inputValue = remember { mutableStateOf(TextFieldValue("0,00")) }
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        AppToolbar(
            navHostController = navHostController,
            topBarTitle = "Adicionar despesa"
        )
        Column(modifier = Modifier.padding(all = PaddingDefault)) {
            Text(
                text = stringResource(id = R.string.fragment_add_edit_expense_text_title),
                color = MaterialTheme.colorScheme.onBackground,
                style = AppTypography.bodyMedium,
            )
            Spacer(modifier = Modifier.size(size = MarginDefault))
            InputField(
                inputValue = inputValue,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                iconId = R.drawable.ic_money
            )
        }
    }
}
