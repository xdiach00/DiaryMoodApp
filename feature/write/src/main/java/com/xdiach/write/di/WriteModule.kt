package com.xdiach.write.di

import androidx.lifecycle.SavedStateHandle
import com.xdiach.write.presentation.viewmodel.WriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val writeModule = module {
    viewModel { (handle: SavedStateHandle) ->
        WriteViewModel(
            savedStateHandle = handle,
            imageToUploadDao = get(),
            imageToDeleteDao = get(),
        )
    }
}
