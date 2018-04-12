package de.florianhansen.roomsample.foreign_key

import android.arch.persistence.room.*
import io.reactivex.Flowable

//https://android.jlelse.eu/android-architecture-components-room-relationships-bf473510c14a
@Entity
data class Person(
        @PrimaryKey(autoGenerate = true) var userId: Long? = null,
        var name: String,
        @Ignore var pets: List<Pet>? = null
)

@Dao
interface PersonDao {

    @Insert
    fun savePerson(person: Person): Long

    @Insert
    fun savePet(pet: Pet): Long

    @Query("SELECT * FROM Person")
    fun getPersons(): Flowable<List<Person>>

    @Query("SELECT * FROM Pet WHERE ownerId = :userId")
    fun getPetsForUser(userId: Long): List<Pet>
}

@Entity(foreignKeys = [(ForeignKey(entity = Person::class, parentColumns = ["userId"], childColumns = ["owner"], onDelete = ForeignKey.CASCADE))])
data class Pet(
        @PrimaryKey(autoGenerate = true) var petId: Long? = null,
        var name: String,
        var ownerId: Long? = null
)