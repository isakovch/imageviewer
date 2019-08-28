package com.example.imageviewerapp.domain

import com.example.imageviewerapp.data.db.ImagesConverter
import com.example.imageviewerapp.data.model.Image
import io.reactivex.Single

class ImagesInteractor(
    private val imagesRepository: ImagesRepository
) {

    fun loadImages(page: Int, count: Int): Single<List<Image>> =
        imagesRepository.getImagesRemote(count, page)
            .map { ImagesConverter.fromNetwork(it) }
}