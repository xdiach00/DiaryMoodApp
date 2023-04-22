package com.xdiach.mongo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xdiach.mongo.database.entity.ImageToUploadEntity

@Dao
interface ImageToUploadDao {

    @Query("SELECT * FROM image_to_upload_table ORDER BY id ASC")
    suspend fun getAllImages(): List<ImageToUploadEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImageToUpload(imageToUpload: ImageToUploadEntity)

    @Query("DELETE FROM image_to_upload_table WHERE id=:imageId")
    suspend fun cleanupImage(imageId: Int)
}
