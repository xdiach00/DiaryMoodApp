@file:Suppress("SwallowedException")

package com.xdiach.home.domain.usecase

import com.xdiach.common.data.repository.ThemeModeRepository
import com.xdiach.common.domain.model.ThemeMode

interface SetThemeModeUseCase {
    suspend operator fun invoke(themeMode: String)
}

class SetThemeModeUseCaseImpl(
    private val themeModeRepository: ThemeModeRepository
) : SetThemeModeUseCase {
    override suspend fun invoke(themeMode: String) {
        val enumValue = try {
            enumValueOf<ThemeMode>(themeMode)
        } catch (e: IllegalArgumentException) {
            ThemeMode.SYSTEM
        }
        themeModeRepository.setThemeMode(enumValue)
    }
}
