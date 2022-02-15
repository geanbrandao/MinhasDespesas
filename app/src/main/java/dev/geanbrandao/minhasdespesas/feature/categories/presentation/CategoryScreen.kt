package dev.geanbrandao.minhasdespesas.feature.categories.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.buttons.ButtonDefault
import dev.geanbrandao.minhasdespesas.common.components.toolbar.AppToolbar
import dev.geanbrandao.minhasdespesas.feature.categories.presentation.components.CategoryItem
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault

@Composable
fun CategoriesScreen(
    navHostController: NavHostController,
) {
    val checkBoxState = remember {
        mutableStateOf(value = false)
    }
    Column(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
    ) {
        AppToolbar(
            stringId = R.string.fragment_categories_title_toolbar,
            navHostController = navHostController
        )
        Column(modifier = Modifier.padding(all = PaddingDefault)) {
            LazyColumn(modifier = Modifier.weight(weight = 1f)) {
                items(listOf(1, 2, 3, 5, 6, 7, 78, 9)) { item ->
                    CategoryItem(categoryName = "Category $item", checkBoxValue = checkBoxState)
                }
            }
            ButtonDefault(stringId = R.string.button_default_text_ok)
        }
    }
}

@Preview
@Composable
fun CategoryScreenPreview() {
    CategoriesScreen(navHostController = rememberNavController())
}