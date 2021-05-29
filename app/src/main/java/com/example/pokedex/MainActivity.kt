package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.pokedex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchFragment = SearchFragment()
        val favoritesFragment = FavoritesFragment()
        val settingsFragment = SettingsFragment()

        setFragment(searchFragment)

        binding.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.search-> {
                    setFragment(searchFragment)
                    binding.bottomNav.menu[1].setIcon(R.drawable.ic_star_0)}
                R.id.favorites -> {setFragment(favoritesFragment)
                    it.setIcon(R.drawable.ic_star_1)
                }
                R.id.settings -> {setFragment(settingsFragment)
                    binding.bottomNav.menu[1].setIcon(R.drawable.ic_star_0)}
            }
            true
        }

    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frag_container, fragment)
            commit()
        }
    }

}