package com.example.imageviewerapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.imageviewerapp.data.model.ImageEntity.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME
)
data class ImageEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: Int,

    @ColumnInfo(name = COLUMN_NAME)
    val name: String,

    @ColumnInfo(name = COLUMN_IMAGE_URL)
    val imageUrl: String
) {

    companion object {
        const val TABLE_NAME = "image"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_IMAGE_URL = "url"
    }
}