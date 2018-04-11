package de.florianhansen.roomsample

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import de.florianhansen.roomsample.di.DaggerAppComponent
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        DaggerAppComponent.builder()
                .app(this)
                .build()
                .inject(this)
    }

    override fun activityInjector() = injector
}