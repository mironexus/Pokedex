package com.example.pokedex.repository

import android.app.Application
import android.util.Log
import com.example.pokedex.PaginatedResponse
import com.example.pokedex.PokemonResponse
import com.example.pokedex.ShortPokemonObject
import com.example.pokedex.database.AppDatabase
import com.example.pokedex.database.Favorite
import com.example.pokedex.database.FavoriteDAO
import com.example.pokedex.network.RetrofitInstance
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.http.Query

class RepositoryImpl(application: Application) {

    val database = AppDatabase.getInstance(application)
    private val favoriteDAO: FavoriteDAO

    init {
        favoriteDAO = database.favoriteDAO
    }

    // networking

    suspend fun getFirstPokemonsFromNetwork(limit: Int, offset: Int): PaginatedResponse {

        val paginatedResponse = RetrofitInstance.api.getPaginatedResponse(limit, offset)

        var paginatedResponseBody = PaginatedResponse("", "", listOf())

        if (paginatedResponse.isSuccessful) paginatedResponseBody = paginatedResponse.body()!! else Log.e("RETROFIT_ERROR", paginatedResponse.code().toString())

        return paginatedResponseBody

    }

    suspend fun getNextPokemonsFromNetwork(url: String): PaginatedResponse {

        val paginatedResponse = RetrofitInstance.api.getHardcodedOffsetAndLimit(url)

        var paginatedResponseBody = PaginatedResponse("", "", listOf())

        if (paginatedResponse.isSuccessful) paginatedResponseBody = paginatedResponse.body()!! else Log.e("RETROFIT_ERROR", paginatedResponse.code().toString())

        return paginatedResponseBody

    }


    suspend fun getSinglePokemonFromNetwork(url: String): PokemonResponse {

        val singlePokemonResponse = RetrofitInstance.api.getSinglePokemon(url)

        var singlePokemonResponseBody = PokemonResponse(0, 0, "", 0, 0, false)

        if (singlePokemonResponse.isSuccessful) singlePokemonResponseBody = singlePokemonResponse.body()!! else Log.e("RETROFIT_ERROR", singlePokemonResponse.code().toString())

        return singlePokemonResponseBody

    }

    suspend fun getSinglePokemonFromNetworkWithId(id: Int): PokemonResponse {

        val singlePokemonResponse = RetrofitInstance.api.getSinglePokemonWithId(id)

        var singlePokemonResponseBody = PokemonResponse(0, 0, "", 0, 0, false)

        if (singlePokemonResponse.isSuccessful) singlePokemonResponseBody = singlePokemonResponse.body()!! else Log.e("RETROFIT_ERROR", singlePokemonResponse.code().toString())

        return singlePokemonResponseBody

    }

    // end networking


    // database
    suspend fun addToFavorites(id: Int) {
        val favorite = Favorite(id, System.currentTimeMillis())
        favoriteDAO.insertFavorite(favorite)
    }

    suspend fun removeFromFavorites(id: Int) {
        favoriteDAO.removeFromFavorites(id)
    }

    suspend fun checkIsFavorite(id: Int): Boolean {
        return favoriteDAO.checkIsFavorite(id)
    }

    suspend fun getFavoritesFromDatabase(): List<Favorite> {
        return favoriteDAO.getFavorites()
    }

    suspend fun reorderFavoritesTransaction(idBeingReplaced: Int, idReplacer: Int) {
        favoriteDAO.reorderFavoritesTransaction(idBeingReplaced, idReplacer)
    }

    suspend fun deleteAllFavorites() {
        favoriteDAO.deleteAllFavorites()
    }

    // end database


}