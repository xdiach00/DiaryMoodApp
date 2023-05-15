package com.xdiach.diarymoodapp

import android.app.Application
import com.xdiach.common.di.commonModule
import com.xdiach.home.di.homeModule
import com.xdiach.mongo.di.mongoModule
import com.xdiach.util.di.utilModule
import com.xdiach.write.di.writeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    mongoModule,
                    utilModule,
                    homeModule,
                    writeModule,
                    commonModule
                )
            )
        }
    }
}
