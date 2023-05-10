package com.xdiach.diarymoodapp

import android.app.Application
import com.xdiach.diarymoodapp.di.dataModule
import com.xdiach.home.di.homeModule
import com.xdiach.write.di.writeModule
import com.xdiach.common.di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.loadKoinModules

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(dataModule)
        }
        loadKoinModules(
            listOf(
                homeModule,
                writeModule,
                commonModule
            )
        )
    }
}
