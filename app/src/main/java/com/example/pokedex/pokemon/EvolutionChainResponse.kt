package com.example.pokedex.pokemon

import com.example.pokedex.Species

data class EvolutionDetails(
    var minLevel: String
)

data class EvolvesTo(
    var evolution_details: EvolutionDetails,
    var species: Species,
    var evolves_to: EvolvesTo
)

data class Chain(
    var evolution_details: Array<Any>,
    var evolves_to: Array<Any>,
    var species: Species
)


data class EvolutionChainResponse(

    var chain: Chain

) {
}