package com.example.imageviewerapp.di

import com.example.imageviewerapp.domain.ImagesInteractor
import com.example.imageviewerapp.domain.ImagesRepository
import com.example.imageviewerapp.domain.ImagesRepositoryImpl
import com.example.imageviewerapp.ui.images.ImagesViewModel
import com.example.imageviewerapp.ui.images.paging.ImagesDataSourceFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object ImagesModule : InjectionModule {

    override fun create() = module {
        factory { ImagesInteractor(get()) }
        single { ImagesRepositoryImpl(get(), get()) } bind ImagesRepository::class
        single { ImagesDataSourceFactory(get()) }
//        single { ImageBoundaryCallbackFactory(get()) }
        viewModel { ImagesViewModel(get()) }
    }
}