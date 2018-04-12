package de.florianhansen.roomsample.relation

import android.arch.persistence.room.*
import io.reactivex.Flowable

//https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1
@Entity
data class GitUser(
        @PrimaryKey(autoGenerate = true) var id: Long?,
        var name: String
)

@Entity
data class Repo(
        @PrimaryKey(autoGenerate = true) var id: Long?,
        var name: String,
        var userId: String
)

class UserAndRepo {

    @Embedded
    var user: GitUser? = null

    @Relation(parentColumn = "id",
            entityColumn = "userId")
    var repos: List<Repo> = mutableListOf()
}

@Dao
interface GitUserDao {

    @Insert
    fun saveRepo(repo: Repo)

    @Insert
    fun saveUser(user: GitUser)

    @Transaction
    @Query("SELECT * FROM gituser")
    fun getUsersAndRepos(): Flowable<List<UserAndRepo>>
}