package dev.geanbrandao.minhasdespesas.localpreferences.data

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    // data store keys
    val KEY_THEME_NAME = stringPreferencesKey("theme_name")
    val KEY_SWIPE_NAME = stringPreferencesKey("swipe_name")
    val KEY_SELECTED_FILTERS = stringPreferencesKey("selected_filters")
}