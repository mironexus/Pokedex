package com.example.pokedex.fragments.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokedex.PokemonResponse
import com.example.pokedex.repository.RepositoryImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FavoritesViewModel(application: Application): AndroidViewModel(application) {

    var favoritesList = MutableLiveData<List<PokemonResponse>>()
    private val repository: RepositoryImpl

    init {
        favoritesList.value = listOf()
        repository = RepositoryImpl(getApplication())
    }

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

    fun removeFromFavorites(id: Int) {
        val waitForDeletion = viewModelScope.launch {
            repository.removeFromFavorites(id)
        }

        viewModelScope.launch {
            waitForDeletion.join()
            getFavorites()
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
        }
    }



}