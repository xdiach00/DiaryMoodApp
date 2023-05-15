package com.xdiach.util.di

import com.xdiach.util.connectivity.NetworkConnectivityObserver
import org.koin.dsl.module

val utilModule = module {
    single {
        NetworkConnectivityObserver(
            context = get(),
        )
    }
}
