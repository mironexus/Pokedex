package com.example.pokedex.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "response_id")
    var response_id: Int,
    @ColumnInfo(name = "timestamp")
    var timestamp: Long
) : Serializable