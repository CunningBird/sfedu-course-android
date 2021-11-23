package com.cunningbird.cats

import android.app.Application
import com.cunningbird.cats.repository.local.AppDatabase
import com.cunningbird.cats.repository.local.LocalInjector

class CatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LocalInjector.appDatabase = AppDatabase.getInstance(this@CatApplication)
    }
}