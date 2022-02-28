package dev.geanbrandao.minhasdespesas.feature.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.common.components.buttons.ButtonDefault
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerFour
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerThree
import dev.geanbrandao.minhasdespesas.common.components.toolbar.AppToolbar
import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb
import dev.geanbrandao.minhasdespesas.feature.domain.model.SelectedCategoriesArg
import dev.geanbrandao.minhasdespesas.feature.presentation.categories.components.CategoryItem
import dev.geanbrandao.minhasdespesas.ui.theme.MarginFour
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault

@Composable
fun CategoriesScreen(
    navHostController: NavHostController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val categoryState = viewModel.state
    val x = navHostController.previousBackStackEntry
        ?.arguments?.getSerializable("SELECTED_CATEGORIES") as SelectedCategoriesArg?
    val data = remember {
        mutableStateOf(x)
    }
    println("DEBUG2 - ${x?.list}")
    Column(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
    ) {
        AppToolbar(
            stringId = R.string.fragment_categories_title_toolbar,
            navHostController = navHostController
        )
        Column(modifier = Modifier.padding(all = PaddingDefault)) {
            LazyColumn(
                modifier = Modifier.weight(weight = 1f),
                contentPadding = PaddingValues(bottom = MarginFour)
            ) {
                items(categoryState.value.categories) { item ->
                    CategoryItem(item) { isChecked: Boolean ->
                        viewModel.onCategoryCheckChange(isChecked = isChecked, categoryDb = item)
                    }
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