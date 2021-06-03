package com.example.pokedex.database

import androidx.room.*

@Dao
interface FavoriteDAO {

    @Query("SELECT * FROM favorites ORDER BY timestamp")
    suspend fun getFavorites() : List<Favorite>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: Favorite)

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE response_id = :id)")
    suspend fun checkIsFavorite(id: Int) : Boolean

    @Query("DELETE FROM favorites WHERE response_id = :id")
    suspend fun removeFromFavorites(id: Int)

//
//    @Query("SELECT response_id FROM favorites")
//    suspend fun getSingleFavorite()
//

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

    @Query("SELECT timestamp FROM favorites WHERE response_id = :id")
    suspend fun getTimestamp(id: Int) : Long

    @Query("UPDATE favorites SET timestamp = :timestamp WHERE response_id = :id")
    suspend fun reorderFavorites(
        id: Int,
        timestamp: Long
    )

    @Transaction
    suspend fun reorderFavoritesTransaction(idBeingReplaced: Int, idReplacer: Int) {

        val timestampOfBeingReplaced = getTimestamp(idBeingReplaced)
        val timestampOfReplacer = getTimestamp(idReplacer)


        // change timestamp of item that is being dropped on
        reorderFavorites(idBeingReplaced, timestampOfReplacer)

        // change timestamp of item that is being moved
        reorderFavorites(idReplacer, timestampOfBeingReplaced)

    }

    @Query("DELETE FROM favorites")
    suspend fun deleteAllFavorites()

}