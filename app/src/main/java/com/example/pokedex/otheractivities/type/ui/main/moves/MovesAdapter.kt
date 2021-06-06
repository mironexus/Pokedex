package com.example.pokedex.otheractivities.type.ui.main.moves

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.otheractivities.type.MoveResponse
import kotlinx.android.synthetic.main.move_card.view.*

class MovesAdapter(private var movesList: List<MoveResponse>) : RecyclerView.Adapter<MovesAdapter.RecycleViewHolder>() {


    fun updateData(movesList: List<MoveResponse>) {
        this.movesList = movesList
        notifyDataSetChanged()
    }


    inner class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val gen: TextView = itemView.gen_value
        val move: TextView = itemView.move_value
        val category: TextView = itemView.category_value
        val power: TextView = itemView.power_value
        val pp: TextView = itemView.pp_value

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.move_card,
            parent, false)
        return RecycleViewHolder(
            itemView
        )
    }


    override fun getItemCount() = movesList.size

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {

        val currentItem = movesList[position]

        if(currentItem != null) {


            holder.gen.text = currentItem.generation.name.substringAfter('-').toUpperCase()

            holder.move.text = currentItem.name

            // commented out because of Attempt to invoke virtual method 'java.lang.String com.example.pokedex.type.DamageClass.getName()' on a null object reference crash
            // only on this part
            //if(currentItem.damage_class.name != null)
              //  holder.category.text = currentItem.damage_class.name

            holder.power.text = currentItem.power.toString()

            holder.pp.text = currentItem.pp.toString()

        }

    }

}