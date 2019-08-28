package com.example.imageviewerapp

import com.example.imageviewerapp.di.DatabaseModule
import com.example.imageviewerapp.di.ImagesModule
import com.example.imageviewerapp.di.NetworkModule

object KoinModules {

    fun create() = listOf(
        NetworkModule.create(),
        ImagesModule.create(),
        DatabaseModule.create()
    )
}
