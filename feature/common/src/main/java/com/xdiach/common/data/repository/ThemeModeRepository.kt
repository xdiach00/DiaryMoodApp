package com.xdiach.common.data.repository

import android.util.Log
import com.xdiach.common.data.datasource.ThemeModeLocalDataSource
import kotlinx.coroutines.flow.first

interface ThemeModeRepository {
    suspend fun getThemeMode(): String
    suspend fun setThemeMode(themeMode: String)
}

internal class ThemeModeRepositoryImpl(
    private val dataSource: ThemeModeLocalDataSource
) : ThemeModeRepository {
    override suspend fun getThemeMode(): String {
        return dataSource.themeMode.first()
    }

    override suspend fun setThemeMode(themeMode: String) {
        Log.d("THEME MODE: ", themeMode)
        dataSource.setThemeMode(themeMode)
    }
}
