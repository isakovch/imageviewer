package com.example.imageviewerapp.di

import androidx.room.Room
import com.example.imageviewerapp.data.db.ImagesDatabase
import com.example.imageviewerapp.data.db.ImagesDatabase.Companion.DATABASE_NAME
import org.koin.dsl.module
import java.util.concurrent.Executors

object DatabaseModule : InjectionModule {

    override fun create() = module {
        single {
            Room
                .databaseBuilder(get(), ImagesDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .setQueryExecutor(Executors.newCachedThreadPool())
                .build()
        }

        single { get<ImagesDatabase>().imagesDao() }
    }
}