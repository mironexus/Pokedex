package com.example.pokedex.fragments.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pokedex.PokemonResponse
import com.example.pokedex.database.FavoriteDAO
import com.example.pokedex.fragments.search.PokemonListDataSource
import com.example.pokedex.repository.RepositoryImpl

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    var pokemonList: LiveData<PagedList<PokemonResponse>>
    private val repository: RepositoryImpl

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()
        pokemonList  = initializedPagedListBuilder(config).build()

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



}