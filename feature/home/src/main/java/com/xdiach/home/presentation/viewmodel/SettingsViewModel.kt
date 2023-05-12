package com.xdiach.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdiach.common.domain.model.ThemeMode
import com.xdiach.home.domain.usecase.GetActiveThemeModeUseCase
import com.xdiach.home.domain.usecase.SetThemeModeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val setThemeModeUseCase: SetThemeModeUseCase,
    private val getActiveThemeModeUseCase: GetActiveThemeModeUseCase
) : ViewModel() {

    fun saveThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch(Dispatchers.IO) {
            setThemeModeUseCase.invoke(themeMode)
        }
    }

    fun getActiveThemeMode(): ThemeMode {
        return getActiveThemeModeUseCase.invoke()
    }
}
