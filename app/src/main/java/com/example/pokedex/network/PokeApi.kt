package com.example.pokedex.network

import com.example.pokedex.PaginatedResponse
import com.example.pokedex.Pokemon
import com.example.pokedex.PokemonResponse
import com.example.pokedex.Species
import com.example.pokedex.pokemon.EvolutionChainResponse
import com.example.pokedex.pokemon.SpeciesResponse
import com.example.pokedex.type.MoveResponse
import com.example.pokedex.type.TypeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PokeApi {

    // pagination
    @GET("/api/v2/pokemon/")
    suspend fun getPaginatedResponse(@Query("limit") limit: Int, @Query("offset") offset: Int): Response<PaginatedResponse>

    @GET
    suspend fun getSinglePokemon(@Url url: String): Response<PokemonResponse>

    @GET
    suspend fun getHardcodedOffsetAndLimit(@Url url: String): Response<PaginatedResponse>
    // end pagination

    // favorites and single item

    @GET("/api/v2/pokemon/{id}")
    suspend fun getSinglePokemonWithId(@Path("id") id: Int): Response<PokemonResponse>

    @GET
    suspend fun getSpecies(@Url url: String): Response<SpeciesResponse>

    @GET
    suspend fun getEvolutionChain(@Url url: String): Response<EvolutionChainResponse>

    // end favorites and single item

    @GET
    suspend fun getType(@Url url: String): Response<TypeResponse>

    @GET
    suspend fun getSingleMove(@Url url: String): Response<MoveResponse>

}