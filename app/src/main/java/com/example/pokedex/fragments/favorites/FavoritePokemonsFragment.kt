package com.example.pokedex.fragments.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentFavoritePokemonsBinding


class FavoritePokemonsFragment : Fragment(), FavoritesListAdapter.OnStartDragListener {

    private var _binding: FragmentFavoritePokemonsBinding? = null
    private val binding get() = _binding!!


    private var mItemTouchHelper: ItemTouchHelper? = null
    private val favoritesViewModel: FavoritesViewModel by activityViewModels()
    var editMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoritePokemonsBinding.inflate(inflater, container, false)
        val view = binding.root

        // general adapter setup
        val favoritesListAdapter = FavoritesListAdapter(
            this,
            favoritesViewModel.favoritesList.value!!,
            {id -> favoritesViewModel.removeFromFavorites(id)},
            {idBeingReplaced, idReplacer -> favoritesViewModel.reorderFavoritesTransaction(idBeingReplaced, idReplacer)}
        )
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.favoritesRecyclerView.adapter = favoritesListAdapter

        // for reordering
        val callback: ItemTouchHelper.Callback = ReorderHelperCallback(favoritesListAdapter)
        mItemTouchHelper = ItemTouchHelper(callback)

        binding.editIcon.setOnClickListener {

            if(!editMode) {
                mItemTouchHelper?.attachToRecyclerView(binding.favoritesRecyclerView)
                binding.editIcon.setImageResource(R.drawable.ic_done)
                editMode = true
            }
            else {
                mItemTouchHelper?.attachToRecyclerView(null)
                binding.editIcon.setImageResource(R.drawable.ic_edit)
                editMode = false
            }

            favoritesListAdapter.setEditable(editMode)
            binding.favoritesRecyclerView.adapter = favoritesListAdapter

        }
        // end reordering


        favoritesViewModel.getFavorites()
        favoritesViewModel.favoritesList.observe(viewLifecycleOwner, Observer {
            favoritesListAdapter.updateData(favoritesViewModel.favoritesList.value!!)
        })

        return view
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
            viewHolder?.let {
                mItemTouchHelper?.startDrag(it)
            }
    }




}