package com.example.pokedex.fragments.search

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.pokedex.PokemonResponse
import com.example.pokedex.network.RetrofitInstance
import com.example.pokedex.repository.RepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class PokemonListDataSource(private val scope: CoroutineScope, private val repositoryInstance: RepositoryImpl): PageKeyedDataSource<String, PokemonResponse>() {

    val INITIAL_OFFSET = 0
    val LIMIT = 20
    //var OFFSET = 20


    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, PokemonResponse>
    ) {

        scope.launch {

            val favoritesList = repositoryInstance.getFavoritesFromDatabase()

            val paginatedResponseBody = repositoryInstance.getFirstPokemonsFromNetwork(LIMIT, INITIAL_OFFSET)

            val asyncTasks = paginatedResponseBody.results.map {
                    ShortPokemonObject -> async { repositoryInstance.getSinglePokemonFromNetwork(ShortPokemonObject.url) }
            }

            val pokemonList: MutableList<PokemonResponse> = asyncTasks.awaitAll().toMutableList()

            // check if pokemon is in favorites
            pokemonList.forEach() {
                for(fav in favoritesList) if(it.id == fav.response_id) it.isFavorite = true
            }

            callback.onResult(pokemonList, paginatedResponseBody.previous, paginatedResponseBody.next)

        }

    }


    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, PokemonResponse>
    ) {
        scope.launch {

            val favoritesList = repositoryInstance.getFavoritesFromDatabase()

            val paginatedResponseBody = repositoryInstance.getNextPokemonsFromNetwork(params.key)

            val asyncTasks = paginatedResponseBody.results.map {
                    ShortPokemonObject -> async { repositoryInstance.getSinglePokemonFromNetwork(ShortPokemonObject.url) }
            }

            val pokemonList: MutableList<PokemonResponse> = asyncTasks.awaitAll().toMutableList()

            // check if pokemon is in favorites
            pokemonList.forEach() {
                for(fav in favoritesList) if(it.id == fav.response_id) it.isFavorite = true
            }

            callback.onResult(pokemonList, paginatedResponseBody.next)

        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, PokemonResponse>
    ) {

    }
}