package com.example.pokedex.pokemon

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityPokemonBinding

private lateinit var binding: ActivityPokemonBinding

class PokemonActivity : AppCompatActivity() {

    private val singlePokemonViewModel: SinglePokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backArrow.setOnClickListener {
            finish()
        }

        val id = intent.getIntExtra("id", 1)
        val isFavorite = intent.getBooleanExtra("isFavorite", false)

        if(isNetworkConnected()) {
            singlePokemonViewModel.getPokemon(id)

            if(isFavorite) binding.setFavorite.setImageResource(R.drawable.ic_star_1)

            singlePokemonViewModel.pokemon.observe(this, Observer {

                binding.collapsingToolbar.title = it.name
                binding.pokedexNum.text = it.order.toString()
                binding.height.text = it.height.toString()
                binding.weight.text = it.weight.toString()
                binding.pokemonImage.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${it.id}.png")

                binding.statsHp.setStatLabel(it.stats[0].stat.name)
                binding.statsHp.setStatValue(it.stats[0].base_stat.toString())
                binding.statsHp.setProgress(it.stats[0].base_stat.toString())
                binding.statsHp.setColor(R.color.error)

                binding.statsAttack.setStatLabel(it.stats[1].stat.name)
                binding.statsAttack.setStatValue(it.stats[1].base_stat.toString())
                binding.statsAttack.setProgress(it.stats[1].base_stat.toString())
                binding.statsAttack.setColor(R.color.coldGray)

                binding.statsDefense.setStatLabel(it.stats[2].stat.name)
                binding.statsDefense.setStatValue(it.stats[2].base_stat.toString())
                binding.statsDefense.setProgress(it.stats[2].base_stat.toString())
                binding.statsDefense.setColor(R.color.tintSecondary)

                binding.statsSpecialAttack.setStatLabel(it.stats[3].stat.name)
                binding.statsSpecialAttack.setStatValue(it.stats[3].base_stat.toString())
                binding.statsSpecialAttack.setProgress(it.stats[3].base_stat.toString())
                binding.statsSpecialAttack.setColor(R.color.tintPrimary)

                binding.statsSpecialDefense.setStatLabel(it.stats[4].stat.name)
                binding.statsSpecialDefense.setStatValue(it.stats[4].base_stat.toString())
                binding.statsSpecialDefense.setProgress(it.stats[4].base_stat.toString())
                binding.statsSpecialDefense.setColor(R.color.dark)

                binding.statsSpeed.setStatLabel(it.stats[5].stat.name)
                binding.statsSpeed.setStatValue(it.stats[5].base_stat.toString())
                binding.statsSpeed.setProgress(it.stats[5].base_stat.toString())
                binding.statsSpeed.setColor(R.color.colorAccent)

            })

        }

    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}