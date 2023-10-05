package dev.geanbrandao.minhasdespesas.feature.presentation.navigation.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.geanbrandao.minhasdespesas.R

sealed class NavBarItem(
    @StringRes val labelId: Int,
    @DrawableRes val iconId: Int,
    @StringRes val contentDescriptionId: Int,
    val route: String,
) {
    object Expenses : NavBarItem(
        labelId = R.string.nav_bar_item_label_expenses,
        iconId = R.drawable.ic_home,
        contentDescriptionId = R.string.nav_bar_item_content_description_expenses,
        route = Screen.Expenses.route,
    )

    object Add : NavBarItem(
        labelId = R.string.nav_bar_item_label_add,
        iconId = R.drawable.ic_add,
        contentDescriptionId = R.string.nav_bar_item_content_description_add,
        route = Screen.Add.route,
    )

    object Profile : NavBarItem(
        labelId = R.string.nav_bar_item_label_profile,
        iconId = R.drawable.ic_profile,
        contentDescriptionId = R.string.nav_bar_item_content_description_profile,
        route = Screen.Profile.route,
    )

    object Charts : NavBarItem(
        labelId = R.string.nav_bar_item_label_charts,
        iconId = R.drawable.ic_pie_chart,
        contentDescriptionId = R.string.nav_bar_item_content_description_charts,
        route = Screen.Charts.route,
    )

    object SplitBill : NavBarItem(
        labelId = R.string.nav_bar_item_label_split_bill,
        iconId = R.drawable.ic_home,
        contentDescriptionId = R.string.nav_bar_item_content_description_expenses,
        route = Screen.SplitBill.route,
    )
}
