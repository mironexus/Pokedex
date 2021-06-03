package com.example.pokedex.fragments.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokedex.databinding.FragmentFavoritePokemonsBinding


class FavoritePokemonsFragment : Fragment() {

    private var _binding: FragmentFavoritePokemonsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoritePokemonsBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

}