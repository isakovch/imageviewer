package com.example.imageviewerapp

import android.app.Application
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        setupTimber()
        setRxErrorHandler()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@App)
            if (BuildConfig.DEBUG) printLogger()
            modules(KoinModules.create())
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setRxErrorHandler() {
        val defaultHandler = RxJavaPlugins.getErrorHandler()
        RxJavaPlugins.setErrorHandler { throwable ->
            when (throwable) {
                is UndeliverableException -> Timber.d(throwable, "Caught UndeliverableException")
                is InterruptedException -> Timber.d(throwable, "Caught InterruptedException")
                else ->
                    if (defaultHandler != null) {
                        defaultHandler.accept(throwable)
                    } else {
                        Thread.currentThread()
                            .uncaughtExceptionHandler
                            .uncaughtException(Thread.currentThread(), throwable)
                    }
            }
        }
    }
}