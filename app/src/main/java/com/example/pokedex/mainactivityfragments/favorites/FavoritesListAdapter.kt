package com.example.pokedex.mainactivityfragments.favorites

import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex.PokemonResponse
import com.example.pokedex.R
import com.example.pokedex.otheractivities.pokemon.PokemonActivity
import kotlinx.android.synthetic.main.pokemon_card.view.*
import java.util.*

class FavoritesListAdapter(val dragStartListener : OnStartDragListener,
                           private var favoritesList: List<PokemonResponse>,
                           val removeFromFavorites: (Int) -> Unit,
                           val reorderFavoritesTransaction: (Int, Int) -> Unit,
                           private val listener: OnItemClickListener
)
    : RecyclerView.Adapter<FavoritesListAdapter.RecycleViewHolder>(), ReorderHelperCallback.ItemTouchHelperAdapter{

    private var isEdit = false

    fun setEditable(edit: Boolean) {
        isEdit = edit
    }

    fun updateData(favoritesList: List<PokemonResponse>) {
        this.favoritesList = favoritesList
        notifyDataSetChanged()
    }

    inner class RecycleViewHolder(itemView: View, val dragStartListener : OnStartDragListener? = null) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        val pokemonName = itemView.pokemon_name
        val pokemonNumber = itemView.pokemon_number
        val pokemonImage = itemView.pokemon_image
        val setFavorite = itemView.set_favorite
        val reorder = itemView.reorder

        var container: CardView = itemView.location_card
        var reorderIcon: ImageView = itemView.reorder
        var innerContainer: ConstraintLayout = itemView.inner_container


        init {
            itemView.setOnClickListener(this)

            reorder.setOnTouchListener(View.OnTouchListener { v, event ->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragStartListener?.onStartDrag(this@RecycleViewHolder)
                }
                false
            })


        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            val currentItem = favoritesList.get(position)
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
            if (v != null) {
                val intent = Intent(v.context, PokemonActivity::class.java)
                if (currentItem != null) {
                    intent.putExtra("id", currentItem.id)
                    intent.putExtra("isFavorite", currentItem.isFavorite)
                    v.context.startActivity(intent)
                }
            }
        }
    }


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.pokemon_card,
            parent, false)
        return RecycleViewHolder(
            itemView
        )
    }

    override fun getItemCount() = favoritesList.size

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val currentItem = favoritesList[position]

        if (currentItem != null) {
            holder.pokemonName.text = currentItem.name.capitalize()
            holder.pokemonNumber.text = currentItem.id.toString()
            holder.pokemonImage.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${currentItem.id}.png")
            holder.setFavorite.setImageResource(R.drawable.ic_star_1)
        }

        holder.setFavorite.setOnClickListener {
            holder.setFavorite.setImageResource(R.drawable.ic_star_0)
            removeFromFavorites(currentItem.id)
        }

        if(isEdit) {
            holder.reorderIcon.visibility = View.VISIBLE
            holder.container.layoutParams.width = (Resources.getSystem().displayMetrics.widthPixels * 0.837).toInt()
            holder.innerContainer.layoutParams.width = (holder.container.layoutParams.width * 0.93).toInt()
        }

    }


    interface OnStartDragListener {

        fun onStartDrag(viewHolder: RecyclerView.ViewHolder?)

    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {

        reorderFavoritesTransaction(favoritesList[toPosition].id, favoritesList[fromPosition].id)

        Collections.swap(favoritesList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)

        return true
    }

    override fun onItemDismiss(position: Int) {
        TODO("Not yet implemented")
    }


}