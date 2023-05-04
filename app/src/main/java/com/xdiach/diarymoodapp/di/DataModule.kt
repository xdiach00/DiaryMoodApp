package com.xdiach.diarymoodapp.di

import androidx.room.Room
import com.xdiach.mongo.database.ImagesDatabase
import com.xdiach.util.Constants.IMAGES_DATABASE
import com.xdiach.util.connectivity.NetworkConnectivityObserver
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = ImagesDatabase::class.java,
            name = IMAGES_DATABASE
        ).build()
    }

    single {
        get<ImagesDatabase>().imageToUploadDao()
    }

    single {
        get<ImagesDatabase>().imageToDeleteDao()
    }

    single {
        NetworkConnectivityObserver(
            context = get(),
        )
    }
}
