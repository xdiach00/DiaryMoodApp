package com.xdiach.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdiach.home.domain.usecase.SetThemeModeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val setThemeModeUseCase: SetThemeModeUseCase
) : ViewModel() {

    fun saveThemeMode(themeMode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setThemeModeUseCase.invoke(themeMode)
        }
    }
}