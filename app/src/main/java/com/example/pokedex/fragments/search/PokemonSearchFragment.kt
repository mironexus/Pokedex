package com.example.pokedex.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.FragmentPokemonSearchBinding

class PokemonSearchFragment : Fragment() {

    private var _binding: FragmentPokemonSearchBinding? = null
    private val binding get() = _binding!!

    private val pokemonListAdapter =
        PokemonListAdapter()
    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPokemonSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        initializeList()

        searchViewModel.getPokemons()

        searchViewModel.pokemonList.observe(viewLifecycleOwner, Observer {
            pokemonListAdapter.submitList(it)
        })



        return view
    }


    private fun initializeList() {
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.searchRecyclerView.adapter = pokemonListAdapter
    }

}