package com.example.imageviewerapp.domain

import androidx.paging.DataSource
import com.example.imageviewerapp.data.db.ImagesConverter
import com.example.imageviewerapp.data.db.ImagesDao
import com.example.imageviewerapp.data.model.Image
import com.example.imageviewerapp.data.model.ImageEntity
import com.example.imageviewerapp.data.model.Images
import com.example.imageviewerapp.data.network.Api
import io.reactivex.Completable
import io.reactivex.Single

interface ImagesRepository {
    fun getImagesRemote(count: Int, page: Int): Single<Images>
    fun getAllImages(): DataSource.Factory<Int, Image>

    fun insertOrUpdateImages(images: List<ImageEntity>): Completable
}

class ImagesRepositoryImpl(
    private val imagesDao: ImagesDao,
    private val api: Api
) : ImagesRepository {

    override fun getImagesRemote(count: Int, page: Int): Single<Images> =
        api.getImages(count, page)

    override fun getAllImages(): DataSource.Factory<Int, Image> =
        imagesDao.getAllImages()
            .mapByPage { page ->
                page.mapNotNull { ImagesConverter.fromDatabase(it) }
            }

    override fun insertOrUpdateImages(images: List<ImageEntity>): Completable =
        Completable.fromCallable { imagesDao.insertOrUpdateImages(images) }
}