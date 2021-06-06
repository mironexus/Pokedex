package com.example.pokedex


data class Species(
    var name: String,
    var url: String
)


data class ShortTypeObject(
    var name: String,
    var url: String
)
data class Type(
    var slot: Int,
    var type: ShortTypeObject
)



data class AbilityShort(
    var name: String,
    var url: String
)
data class AbilityLong(
    var ability: AbilityShort,
    var is_hidden: Boolean,
    var slot: Int
)



data class StatObject(
    var base_stat: Int,
    var stat: Stat
)
data class Stat(
    var name: String,
    var url: String
)

data class PokemonResponse(
    var height: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var weight: Int,
    var isFavorite: Boolean,
    var stats: List<StatObject>,
    var abilities: List<AbilityLong>,
    var species: Species,
    var types: List<Type>
)