package com.example.pokedex.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex.PokemonResponse
import com.example.pokedex.R

class PokemonListAdapter (): PagedListAdapter<PokemonResponse, PokemonListAdapter.PokemonCardViewHolder>(
    DiffUtilCallBack
) {

    object DiffUtilCallBack : DiffUtil.ItemCallback<PokemonResponse>() {
        override fun areItemsTheSame(oldItem: PokemonResponse, newItem: PokemonResponse): Boolean {
            return oldItem.order == newItem.order
        }

        override fun areContentsTheSame(oldItem: PokemonResponse, newItem: PokemonResponse): Boolean {
            return oldItem.order == newItem.order
                    && oldItem.name == newItem.name
                    && oldItem.height == newItem.height
        }

    }

    inner class PokemonCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var name: TextView = itemView.findViewById(R.id.pokemon_name)
        var number: TextView = itemView.findViewById(R.id.pokemon_number)
        var pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_image)
        var setFavorite: ImageView = itemView.findViewById(R.id.set_favorite)


        init {
            itemView.setOnClickListener(this)
        }

        fun bind(data: PokemonResponse) {
            name.text = data.name.capitalize()
            number.text = data.order.toString()
            pokemonImage.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${data.id}.png")
        }

        override fun onClick(v: View?) {

        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonCardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.pokemon_card,
            parent, false)
        return PokemonCardViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: PokemonCardViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }



    }

}