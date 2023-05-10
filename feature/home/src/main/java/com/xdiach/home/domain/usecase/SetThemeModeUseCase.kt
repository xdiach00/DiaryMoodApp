package com.xdiach.home.domain.usecase

import com.xdiach.common.data.repository.ThemeModeRepository

interface SetThemeModeUseCase {
    suspend operator fun invoke(themeMode: String)
}

class SetThemeModeUseCaseImpl(
    private val themeModeRepository: ThemeModeRepository
) : SetThemeModeUseCase {
    override suspend fun invoke(themeMode: String) {
        themeModeRepository.setThemeMode(themeMode)
    }
}
