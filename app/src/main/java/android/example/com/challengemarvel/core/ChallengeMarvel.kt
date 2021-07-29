package android.example.com.challengemarvel.core

import android.app.Application
import android.example.com.challengemarvel.di.authDataSourceModule
import android.example.com.challengemarvel.di.remoteModule
import android.example.com.challengemarvel.di.repoModule
import android.example.com.challengemarvel.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


//App class for koin injection

class ChallengeMarvel : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ChallengeMarvel)
            modules(listOf(authDataSourceModule, repoModule, viewModelModule, remoteModule))
        }
    }
}