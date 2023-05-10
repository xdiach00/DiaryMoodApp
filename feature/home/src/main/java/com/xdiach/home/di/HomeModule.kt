package com.xdiach.home.di

import com.xdiach.home.domain.usecase.SetThemeModeUseCase
import com.xdiach.home.domain.usecase.SetThemeModeUseCaseImpl
import com.xdiach.home.presentation.viewmodel.HomeViewModel
import com.xdiach.home.presentation.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    factory<SetThemeModeUseCase> {
        SetThemeModeUseCaseImpl(
            themeModeRepository = get()
        )
    }

    viewModel {
        HomeViewModel(
            connectivity = get(),
            imageToDeleteDao = get(),
        )
    }

    viewModel {
        SettingsViewModel(
            setThemeModeUseCase = get()
        )
    }
}
