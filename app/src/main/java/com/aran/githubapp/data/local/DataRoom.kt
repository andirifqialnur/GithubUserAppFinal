package com.aran.githubapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class DataRoom(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    var username: String? = null,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "avatar")
    var avatar: String? = null,
)