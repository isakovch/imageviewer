package com.example.imageviewerapp.ui.images.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.imageviewerapp.data.model.Image
import com.example.imageviewerapp.domain.ImagesInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class ImagesDataSource(
    private val imagesInteractor: ImagesInteractor
) : PageKeyedDataSource<Int, Image>() {

    companion object {
        private const val INITIAL_PAGE = 1
        const val INITIAL_ITEMS_COUNT = 30
        const val PAGE_SIZE = 20
    }

    val state: MutableLiveData<PagingState> = MutableLiveData()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Image>) {
        imagesInteractor
            .loadImages(INITIAL_PAGE, PAGE_SIZE)
            .doOnSubscribe { state.postValue(PagingState.InitialLoading) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { operations ->
                    state.postValue(PagingState.Idle)
                    callback.onResult(operations, null, INITIAL_PAGE + 1)
                },
                onError = {
                    Timber.e(it, "Error while fetching initial history")
                    state.postValue(PagingState.Error(it))
                }
            )
            .also { compositeDisposable.add(it) }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        imagesInteractor
            .loadImages(params.key, PAGE_SIZE)
            .doOnSubscribe { state.postValue(PagingState.Loading) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { operations ->
                    state.postValue(PagingState.Idle)
                    callback.onResult(operations, params.key + 1)
                },
                onError = {
                    Timber.e(it, "Error while fetching history")
                    state.postValue(PagingState.Error(it))
                }
            )
            .also { compositeDisposable.add(it) }
    }

    fun dispose() {
        compositeDisposable.dispose()
    }
}