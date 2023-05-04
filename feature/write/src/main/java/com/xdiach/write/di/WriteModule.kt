package com.xdiach.write.di

import com.xdiach.write.presentation.viewmodel.WriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val writeModule = module {
    viewModel {
        WriteViewModel(
            savedStateHandle = get(),
            imageToUploadDao = get(),
            imageToDeleteDao = get(),
        )
    }
}
