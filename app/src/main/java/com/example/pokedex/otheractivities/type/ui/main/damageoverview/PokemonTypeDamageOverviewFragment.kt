package com.example.pokedex.otheractivities.type.ui.main.damageoverview

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonTypeDamageOverviewBinding
import com.example.pokedex.otheractivities.type.ui.main.TypeActivitySharedViewModel


class PokemonTypeDamageOverviewFragment : Fragment() {

    private var _binding: FragmentPokemonTypeDamageOverviewBinding? = null
    private val binding get() = _binding!!

    private val typeActivitySharedViewModel: TypeActivitySharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonTypeDamageOverviewBinding.inflate(inflater, container, false)
        val view = binding.root


        typeActivitySharedViewModel.type.observe(viewLifecycleOwner, Observer {

            typeActivitySharedViewModel.getMoves()

            typeActivitySharedViewModel.getPokemons()

            for (type in it.damage_relations.double_damage_from) {
                val typeButton: TextView = inflater.inflate(R.layout.type_button, null) as TextView
                typeButton.background = getTypeDrawable(type.name)
                typeButton.text = type.name.capitalize()
                binding.defensiveDoubleContainer.addView(typeButton)
            }

            for (type in it.damage_relations.double_damage_to) {
                val typeButton: TextView = inflater.inflate(R.layout.type_button, null) as TextView
                typeButton.background = getTypeDrawable(type.name)
                typeButton.text = type.name.capitalize()
                binding.offensiveDoubleContainer.addView(typeButton)
            }

            for (type in it.damage_relations.half_damage_from) {
                val typeButton: TextView = inflater.inflate(R.layout.type_button, null) as TextView
                typeButton.background = getTypeDrawable(type.name)
                typeButton.text = type.name.capitalize()
                binding.defensiveHalfContainer.addView(typeButton)
            }

            for (type in it.damage_relations.half_damage_to) {
                val typeButton: TextView = inflater.inflate(R.layout.type_button, null) as TextView
                typeButton.background = getTypeDrawable(type.name)
                typeButton.text = type.name.capitalize()
                binding.offensiveHalfContainer.addView(typeButton)
            }

            for (type in it.damage_relations.no_damage_from) {
                val typeButton: TextView = inflater.inflate(R.layout.type_button, null) as TextView
                typeButton.background = getTypeDrawable(type.name)
                typeButton.text = type.name.capitalize()
                binding.defensiveNoneContainer.addView(typeButton)
            }

            for (type in it.damage_relations.no_damage_to) {
                val typeButton: TextView = inflater.inflate(R.layout.type_button, null) as TextView
                typeButton.background = getTypeDrawable(type.name)
                typeButton.text = type.name.capitalize()
                binding.offensiveNoneContainer.addView(typeButton)
            }


        })

        return view
    }


    private fun getTypeDrawable(typeName: String): Drawable {

        var unwrappedDrawable = AppCompatResources.getDrawable(requireContext(), R.drawable.type_button_rounded)!!


        when (typeName) {
            "bug" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_bug))
            "dark" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_dark))
            "dragon" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_dragon))
            "electric" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_electric))
            "fairy" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_fairy))
            "fighting" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_fighting))
            "fire" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_fire))
            "flying" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_flying))
            "ghost" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_ghost))
            "grass" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_grass))
            "ground" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_ground))
            "ice" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_ice))
            "normal" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_normal))
            "poison" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_poison))
            "psychic" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_psychic))
            "rock" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_rock))
            "steel" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_steel))
            "water" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_water))
            "undefined" -> unwrappedDrawable.setTint(resources.getColor(R.color.type_undefined))
        }


        return unwrappedDrawable

    }

}