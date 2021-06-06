package com.example.pokedex.otheractivities.type

data class Generation(
    val name: String,
    val url: String
)

data class DamageClass(
    val name: String,
    val url: String
)

data class MoveResponse(
    var generation: Generation,
    var name: String,
    var damage_class: DamageClass,
    var power: Int,
    var pp: Int
)