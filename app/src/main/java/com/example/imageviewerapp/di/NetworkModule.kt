package com.example.imageviewerapp.di

import com.example.imageviewerapp.data.network.HttpClientBuilder
import com.example.imageviewerapp.data.network.RetrofitBuilder
import org.koin.dsl.module

object NetworkModule : InjectionModule {

    override fun create() = module {
        single { HttpClientBuilder.create() }

        single { RetrofitBuilder.create(get()) }
    }
}