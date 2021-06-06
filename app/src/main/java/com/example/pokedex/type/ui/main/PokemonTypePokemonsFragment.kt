package com.example.pokedex.type.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonTypeDamageOverviewBinding
import com.example.pokedex.databinding.FragmentPokemonTypePokemonsBinding


class PokemonTypePokemonsFragment : Fragment() {

    private var _binding: FragmentPokemonTypePokemonsBinding? = null
    private val binding get() = _binding!!

    private val typeActivitySharedViewModel: TypeActivitySharedViewModel by activityViewModels()

    private val pokemonsAdapter = PokemonsAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonTypePokemonsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.smallPokemonsRecyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.smallPokemonsRecyclerView.adapter = pokemonsAdapter

        typeActivitySharedViewModel.pokemons.observe(viewLifecycleOwner, Observer {

            pokemonsAdapter.updateData(it)

        })


        return view
    }

}