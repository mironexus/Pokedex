package com.example.pokedex.pokemon

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokedex.PokemonResponse
import com.example.pokedex.repository.RepositoryImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class SinglePokemonViewModel(application: Application): AndroidViewModel(application) {

    var pokemon = MutableLiveData<PokemonResponse>()


//    var speciesUrl = MutableLiveData<String>()
//    var evolutionsUrl = MutableLiveData<String>()
//    var namesArray = MutableLiveData<Array<String>>()

    private val repository: RepositoryImpl

    init {
        repository = RepositoryImpl(getApplication())
//        speciesUrl.value = ""
//        evolutionsUrl.value = ""
    }

    fun getPokemon(id: Int) {
        viewModelScope.launch {
            pokemon.value = repository.getSinglePokemonFromNetworkWithId(id)
        }
    }

//    fun getChainUrl() {
//        viewModelScope.launch {
//            evolutionsUrl.value = repository.getEvolutionChain(speciesUrl.value!!)
//        }
//    }
//
//    fun getArray() {
//        viewModelScope.launch {
//            namesArray.value = repository.getEvolutions("https://pokeapi.co/api/v2/evolution-chain/1/")
//        }
//
//    }


}