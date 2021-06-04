package com.example.pokedex.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pokedex.PokemonResponse
import com.example.pokedex.fragments.search.PokemonListDataSource
import com.example.pokedex.repository.RepositoryImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    var pokemonList: LiveData<PagedList<PokemonResponse>>
    var favoritesList = MutableLiveData<List<PokemonResponse>>()
    private val repository: RepositoryImpl

    private val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setEnablePlaceholders(false)
        .build()

    init {

        pokemonList  = initializedPagedListBuilder(config).build()
        favoritesList.value = listOf()

        repository = RepositoryImpl(getApplication())
    }

    fun getPokemons() : LiveData<PagedList<PokemonResponse>> = pokemonList

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<String, PokemonResponse> {

        val dataSourceFactory = object : DataSource.Factory<String, PokemonResponse>() {
            override fun create(): DataSource<String, PokemonResponse> {
                return PokemonListDataSource(
                    viewModelScope, repository
                )
            }
        }

        return LivePagedListBuilder<String, PokemonResponse>(dataSourceFactory, config)

    }

    // other functionality

    // to get Favorites from database in format of PokemonResponse
    fun getFavorites() {
        viewModelScope.launch {
            val favorites = repository.getFavoritesFromDatabase()
            val asyncTasks = favorites.map {
                    Favorite -> async {repository.getSinglePokemonFromNetworkWithId(Favorite.response_id)}
            }
            val pokemonList: MutableList<PokemonResponse> = asyncTasks.awaitAll().toMutableList()
            favoritesList.value = pokemonList
        }
    }

    fun addToFavorites(id: Int) {
        viewModelScope.launch {
            repository.addToFavorites(id)
        }
    }

    fun removeFromFavoritesFromSearch(id: Int) {
        viewModelScope.launch {
            repository.removeFromFavorites(id)
        }
    }

    fun removeFromFavorites(id: Int) {
        val waitForDeletion = viewModelScope.launch {
            repository.removeFromFavorites(id)
        }

        viewModelScope.launch {
            waitForDeletion.join()
            // update favorites list in favorites fragment
            getFavorites()
            // notify list in search fragment
            pokemonList  = initializedPagedListBuilder(config).build()
        }

    }

    fun reorderFavoritesTransaction(idBeingReplaced: Int, idReplacer: Int) {
        viewModelScope.launch {
            repository.reorderFavoritesTransaction(idBeingReplaced, idReplacer)
        }
    }

    fun deleteAllFavorites() {
        viewModelScope.launch {
            repository.deleteAllFavorites()
            pokemonList  = initializedPagedListBuilder(config).build()
        }
    }



}