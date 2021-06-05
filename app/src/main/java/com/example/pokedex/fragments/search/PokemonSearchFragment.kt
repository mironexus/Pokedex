package com.example.pokedex.fragments.search

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.FragmentPokemonSearchBinding
import com.example.pokedex.fragments.SharedViewModel

class PokemonSearchFragment : Fragment() {

    private var _binding: FragmentPokemonSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SharedViewModel by activityViewModels()
    private val pokemonListAdapter =
        PokemonListAdapter(
            {id -> searchViewModel.addToFavorites(id)},
            {id -> searchViewModel.removeFromFavoritesFromSearch(id)}
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPokemonSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        // Adapter setup
        initializeList()


        //region Check for network connection and load data
        if (isNetworkConnected()) {
            binding.searchNoInternet.visibility = View.GONE

            searchViewModel.isDataLoaded.observe(viewLifecycleOwner, Observer {
                if (it) binding.loadingPanel.visibility = View.INVISIBLE
            })

            searchViewModel.getPokemons()

            searchViewModel.pokemonList.observe(viewLifecycleOwner, Observer {
                pokemonListAdapter.submitList(it)
                searchViewModel.isDataLoaded.value = true
            })
        }
        else {
            binding.searchRecyclerView.visibility = View.GONE
        }
        //endregion

        return view
    }



    // For adapter setup
    private fun initializeList() {
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.searchRecyclerView.adapter = pokemonListAdapter
    }

    // For networking
    private fun isNetworkConnected(): Boolean {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


}