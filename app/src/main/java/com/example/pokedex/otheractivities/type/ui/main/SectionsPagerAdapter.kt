package com.example.pokedex.otheractivities.type.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.pokedex.R
import com.example.pokedex.otheractivities.type.ui.main.damageoverview.PokemonTypeDamageOverviewFragment
import com.example.pokedex.otheractivities.type.ui.main.moves.PokemonTypeMovesFragment
import com.example.pokedex.otheractivities.type.ui.main.pokemons.PokemonTypePokemonsFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                PokemonTypeDamageOverviewFragment()
            }
            1 -> {
                PokemonTypeMovesFragment()
            }
            2 -> {
                PokemonTypePokemonsFragment()
            }
            else -> getItem(position)
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 3
    }
}