package dev.geanbrandao.minhasdespesas.feature.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import dev.geanbrandao.minhasdespesas.common.components.spacer.SpacerThree
import dev.geanbrandao.minhasdespesas.common.components.toolbar.AppToolbar
//import dev.geanbrandao.minhasdespesas.feature.domain.model.SelectedCategoriesArg
import dev.geanbrandao.minhasdespesas.ui.theme.PaddingDefault
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    navHostController: NavHostController,
    viewModel: CategoryViewModel = koinViewModel()
) {
//    val categoryState = viewModel.state.value

//    val x = navHostController.previousBackStackEntry
//        ?.arguments?.getSerializable("SELECTED_CATEGORIES") as SelectedCategoriesArg?
//    val data = remember {
//        mutableStateOf(x)
//    }
//    println("DEBUG2 - ${x?.list}")
    Column(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
    ) {
        AppToolbar(
            stringId = R.string.fragment_categories_title_toolbar,
            navHostController = navHostController
        )
        Column(modifier = Modifier.padding(horizontal = PaddingDefault)) {
//            ListCategoryOptions(
//                state = categoryState,
//                onCheckedChangeListener = { isChecked, categoryDb ->
//                    viewModel.onCategoryCheckChange(isChecked = isChecked, categoryDb = categoryDb)
//                },
//                modifier = Modifier.weight(weight = 1f)
//            )
            ButtonDefault(stringId = R.string.button_default_text_ok)
            SpacerThree()
        }
    }
}

@Preview
@Composable
fun CategoryScreenPreview() {
    CategoriesScreen(navHostController = rememberNavController())
}