package de.florianhansen.roomsample.persistance

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import de.florianhansen.roomsample.embedded.User
import de.florianhansen.roomsample.embedded.UserDao
import de.florianhansen.roomsample.foreign_key.Person
import de.florianhansen.roomsample.foreign_key.PersonDao
import de.florianhansen.roomsample.foreign_key.Pet
import de.florianhansen.roomsample.relation.GitUser
import de.florianhansen.roomsample.relation.GitUserDao
import de.florianhansen.roomsample.relation.Repo

@Database(entities = arrayOf(User::class, Pet::class, Person::class, GitUser::class, Repo::class), version = 2)
abstract class SampleDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun personDao(): PersonDao

    abstract fun gitDao(): GitUserDao
}