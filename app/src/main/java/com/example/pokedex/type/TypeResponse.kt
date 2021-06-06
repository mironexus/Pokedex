package com.example.pokedex.type


data class Type(
    var name: String,
    var url: String
)

data class DamageRelations(
    var double_damage_from: List<Type>,
    var double_damage_to: List<Type>,
    var half_damage_from: List<Type>,
    var half_damage_to: List<Type>,
    var no_damage_from: List<Type>,
    var no_damage_to: List<Type>
)

data class Pokemon(
    var name: String,
    var url: String
)

data class Moves(
    var name: String,
    var url: String
)

data class TypeResponse(
    var name: String,
    var damage_relations: DamageRelations,
    var pokemon: List<Pokemon>,
    var moves: List<Moves>
) {
}