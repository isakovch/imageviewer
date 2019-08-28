package com.example.imageviewerapp.ui.images.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.imageviewerapp.data.model.Image
import com.example.imageviewerapp.domain.ImagesInteractor

class ImagesDataSourceFactory(
    private val imagesInteractor: ImagesInteractor
) : DataSource.Factory<Int, Image>() {

    val source = MutableLiveData<ImagesDataSource>()

    private var dataSource: ImagesDataSource? = null

    override fun create(): DataSource<Int, Image> {
        dataSource = ImagesDataSource(imagesInteractor)
        source.postValue(dataSource)
        return dataSource!!
    }

    fun getDataSource() = dataSource
}