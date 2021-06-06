package com.example.pokedex.otheractivities.type.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokedex.PokemonResponse
import com.example.pokedex.otheractivities.type.DamageRelations
import com.example.pokedex.otheractivities.type.MoveResponse
import com.example.pokedex.otheractivities.type.TypeResponse
import com.example.pokedex.repository.RepositoryImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class TypeActivitySharedViewModel(application: Application) : AndroidViewModel(application)  {

    var type = MutableLiveData<TypeResponse>()
    var moves = MutableLiveData<MutableList<MoveResponse>>()
    var pokemons = MutableLiveData<MutableList<PokemonResponse>>()

    private val repository: RepositoryImpl

    init {
        repository = RepositoryImpl(getApplication())
        val emptyDamageRelations =
            DamageRelations(
                listOf(),
                listOf(),
                listOf(),
                listOf(),
                listOf(),
                listOf()
            )
        type.value =
            TypeResponse(
                "",
                emptyDamageRelations,
                listOf(),
                listOf()
            )
        moves.value = mutableListOf()
        pokemons.value = mutableListOf()
    }

    fun getType(url: String) {
        viewModelScope.launch {
            type.value = repository.getType(url)
        }
    }

    fun getMoves() {
        viewModelScope.launch {

            val asyncTasks = type.value?.moves?.map {
                    MoveResponse -> async {repository.getSingleMoveFromNetwork(MoveResponse.url)}
            }
            val movesList =  asyncTasks?.awaitAll()?.toMutableList()!!

            moves.value = movesList

        }
    }

    fun getPokemons() {
        viewModelScope.launch {

            val asyncTasks = type.value?.pokemon?.map {
                    PokemonResponse -> async {repository.getSinglePokemonFromNetwork("https://pokeapi.co/api/v2/pokemon/1/")}
            }
            val pokemonsList =  asyncTasks?.awaitAll()?.toMutableList()!!

            pokemons.value = pokemonsList

        }
    }


}