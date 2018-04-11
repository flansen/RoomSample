package de.florianhansen.roomsample.embedded

import android.arch.persistence.room.*
import io.reactivex.Flowable

//https://developer.android.com/training/data-storage/room/defining-data.html

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM USER")
    fun getAll(): Flowable<List<User>>
}

@Entity
data class User(
        @PrimaryKey(autoGenerate = true) var id: Long?,
        var name: String,
        @Embedded var address: Address?
) {
    constructor() : this(null, "", null)
}

data class Address(
        var street: String,
        var city: String,
        @ColumnInfo(name = "post_code") var postCode: Int
) {
    constructor() : this("", "", 0)
}