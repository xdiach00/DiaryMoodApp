@file:Suppress("SwallowedException")

package com.xdiach.common.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.xdiach.common.domain.model.ThemeMode

interface ThemeModeRepository {
    val themeMode: MutableState<ThemeMode>
    fun setThemeMode(themeMode: ThemeMode)
}

internal class ThemeModeRepositoryImpl(
    applicationContext: Context
) : ThemeModeRepository {
    private val lock = Object()
    private val sharedPreferences: SharedPreferences =
        applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override var themeMode = mutableStateOf(loadThemeMode())

    private fun loadThemeMode(): ThemeMode {
        val themeModeId = sharedPreferences.getString(THEME_MODE, null)
        return try {
            enumValueOf<ThemeMode>(themeModeId ?: ThemeMode.SYSTEM.id)
        } catch (e: IllegalArgumentException) {
            ThemeMode.SYSTEM
        }
    }

    override fun setThemeMode(themeMode: ThemeMode) = synchronized(lock) {
        this.themeMode.value = themeMode
        sharedPreferences.edit()
            .putString(THEME_MODE, themeMode.id)
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "settings"
        private const val THEME_MODE = "theme_mode"
    }
}
