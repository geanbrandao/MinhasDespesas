package dev.geanbrandao.minhasdespesas.common.utils.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.core.database.db.CategoryDb

fun Context.createDefaultCategories() = listOf(
    CategoryDb(
        0,
        getString(R.string.text_default_category_home),
        "ic_house",
        false,
    ),
    CategoryDb(
        1,
        getString(R.string.text_default_category_education),
        "ic_education",
        false,
    ),
    CategoryDb(
        2,
        getString(R.string.text_default_category_eletronics),
        "ic_computer",
        false,
    ),
    CategoryDb(
        3,
        getString(R.string.text_default_category_others),
        "ic_others",
        false,
    ),
    CategoryDb(
        4,
        getString(R.string.text_default_category_restaurant),
        "ic_restaurant",
        false,
    ),
    CategoryDb(
        5,
        getString(R.string.text_default_category_health),
        "ic_healing",
        false,
    ),
    CategoryDb(
        6,
        getString(R.string.text_default_category_services),
        "ic_service",
        false,
    ),
    CategoryDb(
        7,
        getString(R.string.text_default_category_supermarket),
        "ic_supermarket",
        false,
    ),
    CategoryDb(
        8,
        getString(R.string.text_default_category_transport),
        "ic_bus",
        false,
    ),
    CategoryDb(
        9,
        getString(R.string.text_default_category_clothing),
        "ic_store",
        false,
    ),
    CategoryDb(
        10,
        getString(R.string.text_default_category_travel),
        "ic_plane",
        false,
    ),
    CategoryDb(
        11,
        getString(R.string.text_default_category_credit),
        "ic_credit_card",
        false,
    ),
    CategoryDb(
        12,
        getString(R.string.text_default_category_debit),
        "ic_debit_card",
        false,
    ),
    CategoryDb(
        13,
        getString(R.string.text_default_category_money),
        "ic_money",
        false,
    ),
    CategoryDb(
        14,
        getString(R.string.text_default_category_recreation),
        "ic_recreation",
        false,
    ),
    CategoryDb(
        15,
        getString(R.string.text_default_category_church),
        "ic_church",
        false,
    ),
    CategoryDb(
        16,
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
