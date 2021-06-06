package com.example.pokedex.type.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.FragmentPokemonTypeMovesBinding
import com.example.pokedex.fragments.favorites.FavoritesListAdapter
import com.example.pokedex.type.DamageClass
import com.example.pokedex.type.Generation
import com.example.pokedex.type.MoveResponse


class PokemonTypeMovesFragment : Fragment() {

    private var _binding: FragmentPokemonTypeMovesBinding? = null
    private val binding get() = _binding!!

    private val typeActivitySharedViewModel: TypeActivitySharedViewModel by activityViewModels()

    private val movesAdapter = MovesAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonTypeMovesBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.movesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.movesRecyclerView.adapter = movesAdapter


        typeActivitySharedViewModel.moves.observe(viewLifecycleOwner, Observer {

            movesAdapter.updateData(it)

        })


        return view

    }

}