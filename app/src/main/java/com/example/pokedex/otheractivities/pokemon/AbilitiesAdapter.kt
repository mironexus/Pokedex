package com.example.pokedex.otheractivities.pokemon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.AbilityLong
import com.example.pokedex.R
import kotlinx.android.synthetic.main.ability_card.view.*


class AbilitiesAdapter(private var longAbilities: List<AbilityLong>) : RecyclerView.Adapter<AbilitiesAdapter.RecycleViewHolder>(){

    inner class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val abilityName: TextView = itemView.name
        val hidden: TextView = itemView.hidden_label

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.ability_card,
            parent, false)
        return RecycleViewHolder(
            itemView
        )
    }


    override fun getItemCount() = longAbilities.size

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {

        val currentItem = longAbilities[position]

        if(currentItem != null) {

            holder.abilityName.text = currentItem.ability.name.capitalize()

            if (currentItem.is_hidden) holder.hidden.visibility =
                View.VISIBLE else holder.hidden.visibility = View.GONE
        }

    }


}