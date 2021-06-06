package com.example.pokedex.repository

import android.app.Application
import android.util.Log
import com.example.pokedex.mainactivityfragments.search.PaginatedResponse
import com.example.pokedex.PokemonResponse
import com.example.pokedex.Species
import com.example.pokedex.database.AppDatabase
import com.example.pokedex.database.Favorite
import com.example.pokedex.database.FavoriteDAO
import com.example.pokedex.network.RetrofitInstance
import com.example.pokedex.otheractivities.type.*

class RepositoryImpl(application: Application) {

    val database = AppDatabase.getInstance(application)
    private val favoriteDAO: FavoriteDAO

    init {
        favoriteDAO = database.favoriteDAO
    }



    //region Networking
    suspend fun getFirstPokemonsFromNetwork(limit: Int, offset: Int): PaginatedResponse {

        val paginatedResponse = RetrofitInstance.api.getPaginatedResponse(limit, offset)

        var paginatedResponseBody =
            PaginatedResponse(
                "",
                "",
                listOf()
            )

        if (paginatedResponse.isSuccessful) paginatedResponseBody = paginatedResponse.body()!! else Log.e("RETROFIT_ERROR", paginatedResponse.code().toString())

        return paginatedResponseBody

    }

    suspend fun getNextPokemonsFromNetwork(url: String): PaginatedResponse {

        val paginatedResponse = RetrofitInstance.api.getHardcodedOffsetAndLimit(url)

        var paginatedResponseBody =
            PaginatedResponse(
                "",
                "",
                listOf()
            )

        if (paginatedResponse.isSuccessful) paginatedResponseBody = paginatedResponse.body()!! else Log.e("RETROFIT_ERROR", paginatedResponse.code().toString())

        return paginatedResponseBody

    }


    suspend fun getSinglePokemonFromNetwork(url: String): PokemonResponse {

        val singlePokemonResponse = RetrofitInstance.api.getSinglePokemon(url)

        var singlePokemonResponseBody = PokemonResponse(0, 0, "", 0, 0, false, listOf(), listOf(), Species("", ""), listOf())

        if (singlePokemonResponse.isSuccessful) singlePokemonResponseBody = singlePokemonResponse.body()!! else Log.e("RETROFIT_ERROR", singlePokemonResponse.code().toString())

        return singlePokemonResponseBody

    }

    // for favorites
    suspend fun getSinglePokemonFromNetworkWithId(id: Int): PokemonResponse {

        val singlePokemonResponse = RetrofitInstance.api.getSinglePokemonWithId(id)

        var singlePokemonResponseBody = PokemonResponse(0, 0, "", 0, 0, false, listOf(), listOf(), Species("", ""), listOf())

        if (singlePokemonResponse.isSuccessful) singlePokemonResponseBody = singlePokemonResponse.body()!! else Log.e("RETROFIT_ERROR", singlePokemonResponse.code().toString())

        return singlePokemonResponseBody

    }
    //endregion

//    suspend fun getEvolutionChain(url: String) : String {
//        val speciesResponseFromApi = RetrofitInstance.api.getSpecies(url)
//
//        var evolutionChainUrl = ""
//
//        if (speciesResponseFromApi.isSuccessful) evolutionChainUrl = speciesResponseFromApi.body()?.evolutionChain.toString() else Log.e("RETROFIT_ERROR", speciesResponseFromApi.code().toString())
//
//        return evolutionChainUrl
//    }
//
//    suspend fun getEvolutions(url: String): Array<String> {
//
//       val evolutionChainResponseFromApi = RetrofitInstance.api.getEvolutionChain(url)
//
//        var firstName = "aaa"
//        var secondName = "bbb"
//        var thirdName = "ccc"
//
//
//        var evolutionChain: EvolutionChainResponse
//        if (evolutionChainResponseFromApi.isSuccessful) {
//            evolutionChain = evolutionChainResponseFromApi.body()!!
//
//            firstName = evolutionChain.chain.species.name
////            secondName = evolutionChain.chain.evolves_to.species.name
////            thirdName = evolutionChain.chain.evolves_to.evolves_to.species.name
//        }
//        else
//            Log.e("RETROFIT_ERROR", evolutionChainResponseFromApi.code().toString())
//
//
//
//        var array = arrayOf(firstName, secondName, thirdName)
//
//        return array
//
//
//    }

    suspend fun getType(url: String): TypeResponse {

        val singleTypeResponseFromNetwork = RetrofitInstance.api.getType(url)

        var emptyDamageRelations =
            DamageRelations(
                listOf(),
                listOf(),
                listOf(),
                listOf(),
                listOf(),
                listOf()
            )

        var singleTypeResponse =
            TypeResponse(
                "",
                emptyDamageRelations,
                listOf(),
                listOf()
            )

        if (singleTypeResponseFromNetwork.isSuccessful) singleTypeResponse = singleTypeResponseFromNetwork.body()!! else Log.e("RETROFIT_ERROR", singleTypeResponseFromNetwork.code().toString())

        return singleTypeResponse

    }

    suspend fun getSingleMoveFromNetwork(url: String): MoveResponse {

        val singleMoveResponseFromNetwork = RetrofitInstance.api.getSingleMove(url)

        var singleMoveResponse =
            MoveResponse(
                Generation(
                    "",
                    ""
                ),
                "",
                DamageClass(
                    "",
                    ""
                ),
                0,
                0
            )

        if (singleMoveResponseFromNetwork.isSuccessful) singleMoveResponse = singleMoveResponseFromNetwork.body()!! else Log.e("RETROFIT_ERROR", singleMoveResponseFromNetwork.code().toString())

        return singleMoveResponse

    }


    //region Database
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
    //endregion


}