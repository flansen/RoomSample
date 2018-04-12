package de.florianhansen.roomsample.foreign_key

import android.arch.persistence.room.*

@Entity
data class Person(
        @PrimaryKey(autoGenerate = true) var userId: Long?,
        var name: String,
        @Ignore var pets: List<Pet>?
)

@Dao
interface PersonDao {

    @Insert
    fun savePerson(person: Person)

    @Insert
    fun savePet(pet: Pet)

    @Query("SELECT * FROM Person")
    fun getPersons(): List<Person>

    @Query("SELECT * FROM Pet WHERE ownerId = :userId")
    fun getPetsForUser(userId: Long): List<Pet>
}

@Entity(foreignKeys = [(ForeignKey(entity = Person::class, parentColumns = ["userId"], childColumns = ["owner"], onDelete = ForeignKey.CASCADE))])
data class Pet(
        @PrimaryKey(autoGenerate = true) var petId: Long?,
        var name: String,
        var ownerId: Long
)