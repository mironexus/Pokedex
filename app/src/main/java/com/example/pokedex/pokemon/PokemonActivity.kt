package com.example.pokedex.pokemon

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityPokemonBinding
import com.example.pokedex.type.PokemonTypeActivity
import com.google.android.flexbox.FlexboxLayout
import org.w3c.dom.Text

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

                //region Stats
                binding.collapsingToolbar.title = it.name.capitalize()
                binding.pokedexNum.text = it.order.toString()
                binding.height.text = it.height.toString()
                binding.weight.text = it.weight.toString()
                binding.pokemonImage.load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${it.id}.png")

                binding.statsHp.setStatValue(it.stats[0].base_stat.toString())
                binding.statsHp.setProgress(it.stats[0].base_stat.toString())
                binding.statsHp.setColor(R.color.stat_color_hp)

                binding.statsAttack.setStatValue(it.stats[1].base_stat.toString())
                binding.statsAttack.setProgress(it.stats[1].base_stat.toString())
                binding.statsAttack.setColor(R.color.stat_color_att)

                binding.statsDefense.setStatValue(it.stats[2].base_stat.toString())
                binding.statsDefense.setProgress(it.stats[2].base_stat.toString())
                binding.statsDefense.setColor(R.color.stat_color_def)

                binding.statsSpecialAttack.setStatValue(it.stats[3].base_stat.toString())
                binding.statsSpecialAttack.setProgress(it.stats[3].base_stat.toString())
                binding.statsSpecialAttack.setColor(R.color.stat_color_sp_att)

                binding.statsSpecialDefense.setStatValue(it.stats[4].base_stat.toString())
                binding.statsSpecialDefense.setProgress(it.stats[4].base_stat.toString())
                binding.statsSpecialDefense.setColor(R.color.stat_color_sp_def)

                binding.statsSpeed.setStatValue(it.stats[5].base_stat.toString())
                binding.statsSpeed.setProgress(it.stats[5].base_stat.toString())
                binding.statsSpeed.setColor(R.color.stat_color_spd)

                binding.statsTotal.text = (it.stats[0].base_stat + it.stats[1].base_stat + it.stats[2].base_stat + it.stats[3].base_stat + it.stats[4].base_stat + it.stats[5].base_stat).toString()
                //endregion


                //region Abilities
                val abilitiesAdapter =
                    AbilitiesAdapter(
                        it.abilities
                    )
                binding.abilitiesRecyclerView.adapter = abilitiesAdapter
                val layoutManagerAbility = LinearLayoutManager(applicationContext)
                layoutManagerAbility.orientation = LinearLayoutManager.HORIZONTAL
                binding.abilitiesRecyclerView.layoutManager = layoutManagerAbility
                //endregion


                val inflater =
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                for(outerType in it.types) {

                    val typeButton: TextView = inflater.inflate(R.layout.type_button, null) as TextView

                    typeButton.background = getTypeDrawable(outerType.type.name)

                    typeButton.text = outerType.type.name.capitalize()

                    typeButton.setOnClickListener {
                        val intent = Intent(it.context, PokemonTypeActivity::class.java)
                        intent.putExtra("url", outerType.type.url)
                        it.context.startActivity(intent)
                    }

                    binding.typesContainer.addView(typeButton)
                }


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



    private fun getTypeDrawable(typeName: String): Drawable {

        var unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.type_button_rounded)!!


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