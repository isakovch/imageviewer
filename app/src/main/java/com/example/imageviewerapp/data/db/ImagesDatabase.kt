package com.example.imageviewerapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imageviewerapp.data.db.ImagesDatabase.Companion.DATABASE_VERSION
import com.example.imageviewerapp.data.model.ImageEntity

@Database(
    version = DATABASE_VERSION,
    entities = [ImageEntity::class],
    exportSchema = false
)
abstract class ImagesDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "images"
        const val DATABASE_VERSION = 1
    }

    abstract fun imagesDao(): ImagesDao
}