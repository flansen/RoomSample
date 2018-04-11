package de.florianhansen.roomsample.di

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import de.florianhansen.roomsample.persistance.SampleDatabase
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun database(application: Application) =
            Room.databaseBuilder(application, SampleDatabase::class.java, "sample.db").build()

    @Provides
    @Singleton
    fun userDao(db: SampleDatabase) = db.userDao()
}