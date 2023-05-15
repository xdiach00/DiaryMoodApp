package com.xdiach.home.domain.usecase

import com.xdiach.common.data.repository.ThemeModeRepository
import com.xdiach.common.domain.model.ThemeMode

interface GetActiveThemeModeUseCase {
    operator fun invoke(): ThemeMode
}

class GetActiveThemeModeUseCaseImpl(
    private val themeModeRepository: ThemeModeRepository
) : GetActiveThemeModeUseCase {
    override fun invoke(): ThemeMode {
        return themeModeRepository.themeMode.value
    }
}
