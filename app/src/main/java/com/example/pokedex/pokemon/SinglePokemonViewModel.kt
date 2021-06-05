package com.example.pokedex.pokemon

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokedex.PokemonResponse
import com.example.pokedex.repository.RepositoryImpl
import kotlinx.coroutines.launch

class SinglePokemonViewModel(application: Application): AndroidViewModel(application) {

    var pokemon = MutableLiveData<PokemonResponse>()

    private val repository: RepositoryImpl

    init {
        repository = RepositoryImpl(getApplication())
    }

    fun getPokemon(id: Int) {
        viewModelScope.launch {
            pokemon.value = repository.getSinglePokemonFromNetworkWithId(id)
        }
    }

}