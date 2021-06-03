package com.example.pokedex.network

import com.example.pokedex.PaginatedResponse
import com.example.pokedex.Pokemon
import com.example.pokedex.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokeApi {

    @GET("/api/v2/pokemon/")
    suspend fun getPaginatedResponse(@Query("limit") limit: Int, @Query("offset") offset: Int): Response<PaginatedResponse>

    @GET
    suspend fun getSinglePokemon(@Url url: String): Response<PokemonResponse>

    @GET
    suspend fun getHardcodedOffsetAndLimit(@Url url: String): Response<PaginatedResponse>

}