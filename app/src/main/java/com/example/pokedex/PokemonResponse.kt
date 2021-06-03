package com.example.pokedex

data class ShortAbilityObject(
    var name: String,
    var url: String
)

data class AbilitiesListObject(
    var ShortAbilityObjectList: List<ShortAbilityObject>,
    var is_hidden: Boolean,
    var slot: Int
)

data class ShortTypeObject(
    var name: String,
    var url: String
)

data class TypesListObject(
    var ShortTypeObjectList: List<ShortTypeObject>,
    var slot: Int
)

data class PokemonResponse(
    var height: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var weight: Int,
    var isFavorite: Boolean
)