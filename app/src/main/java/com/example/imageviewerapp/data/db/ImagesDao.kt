package com.example.imageviewerapp.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imageviewerapp.data.model.ImageEntity

@Dao
interface ImagesDao {

    @Query("SELECT * FROM ${ImageEntity.TABLE_NAME}")
    fun getAllImages(): DataSource.Factory<Int, ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateImages(operations: List<ImageEntity>)
}