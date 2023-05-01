package com.xdiach.mongo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xdiach.mongo.database.entity.ImageToDeleteEntity

@Dao
interface ImageToDeleteDao {

    @Query("SELECT * FROM image_to_delete_table ORDER BY id ASC")
    suspend fun getAllImages(): List<ImageToDeleteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImageToDelete(imageToDelete: ImageToDeleteEntity)

    @Query("DELETE FROM image_to_delete_table WHERE id=:imageId")
    suspend fun cleanupImage(imageId: Int)
}
