package de.florianhansen.roomsample.persistance

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import de.florianhansen.roomsample.embedded.User
import de.florianhansen.roomsample.embedded.UserDao

@Database(entities = arrayOf(User::class), version = 1)
abstract class SampleDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}