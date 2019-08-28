package com.example.imageviewerapp.ui.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.imageviewerapp.data.model.Image
import com.example.imageviewerapp.ui.base.BaseViewModel
import com.example.imageviewerapp.ui.images.paging.ImagesDataSource.Companion.INITIAL_ITEMS_COUNT
import com.example.imageviewerapp.ui.images.paging.ImagesDataSource.Companion.PAGE_SIZE
import com.example.imageviewerapp.ui.images.paging.ImagesDataSourceFactory
import com.example.imageviewerapp.ui.images.paging.PagingState

class ImagesViewModel(
    private val imagesDataSourceFactory: ImagesDataSourceFactory
) : BaseViewModel() {

    val stateLiveData: LiveData<PagingState> = Transformations
        .switchMap(imagesDataSourceFactory.source) { it.state }

    val imagesLiveData: LiveData<PagedList<Image>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(INITIAL_ITEMS_COUNT)
            .setEnablePlaceholders(false)
            .build()

        imagesLiveData = LivePagedListBuilder(imagesDataSourceFactory, config).build()
    }

    fun reload() {
        imagesDataSourceFactory.getDataSource()?.invalidate()
    }

    override fun onCleared() {
        super.onCleared()
        imagesDataSourceFactory.getDataSource()?.dispose()
    }
}