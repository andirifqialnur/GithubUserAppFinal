package com.aran.githubapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataDao {
    @Insert
    fun insert(user: DataRoom)

    @Query("DELETE from data where username= :username")
    fun delete(username: String)

    @Query("SELECT * from data order by id asc")
    fun getAllData(): List<DataRoom>

    @Query("SELECT EXISTS(SELECT * from data where username= :username)")
    fun dataExist(username: String): Boolean
}