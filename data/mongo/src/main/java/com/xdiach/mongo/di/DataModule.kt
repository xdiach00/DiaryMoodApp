package com.xdiach.mongo.di

import androidx.room.Room
import com.xdiach.mongo.database.ImagesDatabase
import com.xdiach.util.Constants
import org.koin.dsl.module

val mongoModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = ImagesDatabase::class.java,
            name = Constants.IMAGES_DATABASE
        ).build()
    }

    single {
        get<ImagesDatabase>().imageToUploadDao()
    }

    single {
        get<ImagesDatabase>().imageToDeleteDao()
    }
}
