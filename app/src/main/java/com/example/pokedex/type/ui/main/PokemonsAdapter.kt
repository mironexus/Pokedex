package com.example.pokedex.type.ui.main

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex.PokemonResponse
import com.example.pokedex.R

import kotlinx.android.synthetic.main.small_pokemon_card.view.*

class PokemonsAdapter(private var pokemonsList: List<PokemonResponse>) : RecyclerView.Adapter<PokemonsAdapter.RecycleViewHolder>() {


    fun updateData(pokemonsList: List<PokemonResponse>) {
        this.pokemonsList = pokemonsList
        notifyDataSetChanged()
    }


    inner class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.small_pokemon_image
        val name: TextView = itemView.small_pokemon_name


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.small_pokemon_card,
            parent, false)
        return RecycleViewHolder(
            itemView
        )
    }


    override fun getItemCount() = pokemonsList.size

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {

        val currentItem = pokemonsList[position]

        if(currentItem != null) {

            holder.image.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${currentItem.id}.png")

            holder.name.text = currentItem.name.capitalize()

        }

    }

}