package com.xdiach.diarymoodapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xdiach.diarymoodapp.util.Constants.IMAGE_TO_DELETE_TABLE

@Entity(tableName = IMAGE_TO_DELETE_TABLE)
data class ImageToDeleteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remoteImagePath: String
)
