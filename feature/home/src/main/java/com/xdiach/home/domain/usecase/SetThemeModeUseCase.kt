@file:Suppress("SwallowedException")

package com.xdiach.home.domain.usecase

import com.xdiach.common.data.repository.ThemeModeRepository
import com.xdiach.common.domain.model.ThemeMode

interface SetThemeModeUseCase {
    suspend operator fun invoke(themeMode: ThemeMode)
}

class SetThemeModeUseCaseImpl(
    private val themeModeRepository: ThemeModeRepository
) : SetThemeModeUseCase {
    override suspend fun invoke(themeMode: ThemeMode) {
        themeModeRepository.setThemeMode(themeMode)
    }
}
