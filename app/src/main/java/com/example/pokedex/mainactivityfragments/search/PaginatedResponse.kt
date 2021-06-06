package com.example.pokedex.mainactivityfragments.search

data class ShortPokemonObject(
    var name: String,
    var url: String
)

data class PaginatedResponse(
    var next: String,
    var previous: String,
    var results: List<ShortPokemonObject>
)