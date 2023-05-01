package com.xdiach.mongo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xdiach.mongo.database.entity.ImageToDeleteEntity
import com.xdiach.mongo.database.entity.ImageToUploadEntity

@Database(
    entities = [ImageToUploadEntity::class, ImageToDeleteEntity::class],
    version = 2,
    exportSchema = false
)
abstract class ImagesDatabase : RoomDatabase() {
    abstract fun imageToUploadDao(): ImageToUploadDao
    abstract fun imageToDeleteDao(): ImageToDeleteDao
}
