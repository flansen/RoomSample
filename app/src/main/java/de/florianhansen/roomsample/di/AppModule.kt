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
    //Room.databaseBuilder(application, SampleDatabase::class.java, "sample.db")
            Room.inMemoryDatabaseBuilder(application, SampleDatabase::class.java)
                    .fallbackToDestructiveMigration()
                    .build()

    @Provides
    @Singleton
    fun userDao(db: SampleDatabase) = db.userDao()

    @Provides
    @Singleton
    fun gitDao(db: SampleDatabase) = db.gitDao()

    @Provides
    @Singleton
    fun personDao(db: SampleDatabase) = db.personDao()
}