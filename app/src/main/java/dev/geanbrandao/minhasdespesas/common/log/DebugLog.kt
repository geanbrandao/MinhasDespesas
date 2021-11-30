package dev.geanbrandao.minhasdespesas.common.log

import android.util.Log

data class DebugLog(private val tag: String) {

    fun debug(message: String) {
        Log.d(tag, message)
    }
}
