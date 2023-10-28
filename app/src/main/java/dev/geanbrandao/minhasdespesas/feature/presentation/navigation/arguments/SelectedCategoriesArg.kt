package dev.geanbrandao.minhasdespesas.feature.presentation.navigation.arguments

import android.os.Parcelable
import androidx.navigation.NavHostController
import br.dev.geanbrandao.common.domain.parcelable
import dev.geanbrandao.minhasdespesas.domain.model.Category
import kotlinx.parcelize.Parcelize

const val KEY_ARG_SELECTED_CATEGORIES = "keyArgSelectedCategories"

@Parcelize
data class SelectedCategoriesArg(
    val selectedCategories: List<Category>,
): Parcelable {

    companion object {
        fun getSelectedCategories(navHostController: NavHostController): SelectedCategoriesArg {
            return navHostController.previousBackStackEntry?.arguments?.parcelable(
                KEY_ARG_SELECTED_CATEGORIES
            ) ?: SelectedCategoriesArg(emptyList())
        }
    }
}
