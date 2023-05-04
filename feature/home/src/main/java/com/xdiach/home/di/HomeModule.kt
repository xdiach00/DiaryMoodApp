package com.xdiach.home.di

import com.xdiach.home.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel(
            connectivity = get(),
            imageToDeleteDao = get(),
        )
    }
}
