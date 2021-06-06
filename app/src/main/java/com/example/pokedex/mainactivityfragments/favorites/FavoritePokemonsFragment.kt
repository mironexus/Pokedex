package com.example.pokedex.mainactivityfragments.favorites

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import com.example.pokedex.mainactivityfragments.SharedViewModel


class FavoritePokemonsFragment : Fragment(), FavoritesListAdapter.OnStartDragListener, FavoritesListAdapter.OnItemClickListener {

    private var _binding: FragmentFavoritePokemonsBinding? = null
    private val binding get() = _binding!!

    private var mItemTouchHelper: ItemTouchHelper? = null
    private val favoritesViewModel: SharedViewModel by activityViewModels()
    private val favoritesListAdapter = FavoritesListAdapter(
        this,
        listOf(),
        {id -> favoritesViewModel.removeFromFavorites(id)},
        {idBeingReplaced, idReplacer -> favoritesViewModel.reorderFavoritesTransaction(idBeingReplaced, idReplacer)},
        this
    )
    private var editMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoritePokemonsBinding.inflate(inflater, container, false)
        val view = binding.root

        // Adapter setup
        initializeList()


        //region For reordering
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
        //endregion


        //region Check for network connection and load data
        if (isNetworkConnected()) {
            binding.favoritesNoInternet.visibility = View.GONE

            favoritesViewModel.getFavorites()

            favoritesViewModel.favoritesList.observe(viewLifecycleOwner, Observer {

                favoritesListAdapter.updateData(favoritesViewModel.favoritesList.value!!)

                if(!favoritesViewModel.favoritesList.value.isNullOrEmpty())
                    binding.favoritesPlaceholder.visibility = View.GONE
                else
                    binding.favoritesPlaceholder.visibility = View.VISIBLE

            })
        }
        else {
            binding.favoritesPlaceholder.visibility = View.GONE
            binding.favoritesRecyclerView.visibility = View.GONE
        }
        //endregion

        return view
    }



    // For adapter setup
    private fun initializeList() {
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.favoritesRecyclerView.adapter = favoritesListAdapter
    }

    // For reordering
    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
            viewHolder?.let {
                mItemTouchHelper?.startDrag(it)
            }
    }

    // For networking
    private fun isNetworkConnected(): Boolean {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onItemClick(position: Int) {

    }


}