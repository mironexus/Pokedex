package com.example.pokedex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDAO {

//    @Query("SELECT * FROM favorites ORDER BY timestamp")
//    suspend fun getAllFavorites()
//
//    @Insert
//    suspend fun insertFavorite()
//
//    @Query("SELECT response_id FROM favorites")
//    suspend fun getSingleFavorite()
//
//    @Query("DELETE FROM favorites")
//    suspend fun deleteAllFavorites()
//
//    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE response_id = :responseId)")
//    suspend fun checkIfFavorite(responseId: Int) : Boolean
//
//
//    @Query("UPDATE favorites SET timestamp = :timestampReplacer WHERE response_id = :idBeingReplaced")
//    suspend fun reorderFavoritesOld(
//        idBeingReplaced: Int,
//        timestampReplacer: Long
//    )
//
//    @Query("UPDATE favorites SET timestamp = :timestampBeingReplaced WHERE response_id = :idReplacer")
//    suspend fun reorderFavoritesNew(
//        timestampBeingReplaced: Long,
//        idReplacer: Int
//    )

}