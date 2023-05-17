package com.xdiach.common.di

import com.xdiach.common.data.repository.ThemeModeRepository
import com.xdiach.common.data.repository.ThemeModeRepositoryImpl
import org.koin.dsl.module

val commonModule = module {

    single<ThemeModeRepository> {
        ThemeModeRepositoryImpl(
            applicationContext = get()
        )
    }
}
