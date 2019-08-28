package com.example.imageviewerapp.data.db

import com.example.imageviewerapp.data.model.Image
import com.example.imageviewerapp.data.model.ImageEntity
import com.example.imageviewerapp.data.model.Images

object ImagesConverter {

    fun fromNetwork(images: Images): List<Image> =
        images.results.map {
            Image(it.id, it.name, it.image)
        }

    fun toDatabase(images: Images): List<ImageEntity> =
        images.results.map {
            ImageEntity(it.id, it.name, it.image)
        }

    fun fromDatabase(image: ImageEntity): Image =
        Image(image.id, image.name, image.imageUrl)
}