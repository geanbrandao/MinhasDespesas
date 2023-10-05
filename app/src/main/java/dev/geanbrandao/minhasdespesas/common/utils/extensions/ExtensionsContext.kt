package dev.geanbrandao.minhasdespesas.common.utils.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.data.entity.CategoryEntity

fun Context.createDefaultCategories() = listOf(
    CategoryEntity(
        0,
        getString(R.string.text_default_category_home),
        "ic_house",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_education),
        "ic_education",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_eletronics),
        "ic_computer",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_others),
        "ic_others",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_restaurant),
        "ic_restaurant",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_health),
        "ic_healing",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_services),
        "ic_service",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_supermarket),
        "ic_supermarket",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_transport),
        "ic_bus",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_clothing),
        "ic_store",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_travel),
        "ic_plane",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_credit),
        "ic_credit_card",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_debit),
        "ic_debit_card",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_money),
        "ic_money",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_recreation),
        "ic_recreation",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_church),
        "ic_church",
        false,
    ),
    CategoryEntity(
        0,
        getString(R.string.text_default_category_gym),
        "ic_gym",
        false,
    )
)

fun Context.getIconFromString(name: String, colorId: Int): Drawable? {
    return getIcon(resources.getIdentifier(name, "drawable", packageName), colorId)
}

fun Context.getIconIdFromString(name: String) : Int {
    return resources.getIdentifier(name, "drawable", packageName)
}

fun Context.getIcon(iconId: Int, colorId: Int): Drawable? {
    val icon = ContextCompat.getDrawable(this, iconId)
    icon?.let {
        val h: Int = it.intrinsicHeight
        val w: Int = it.intrinsicWidth
        it.setBounds(0, 0, w, h)
        it.setTint(ContextCompat.getColor(this, colorId))
        return icon
    } ?: run {
        return null
    }
}

