package com.example.pokedex.type

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityPokemonTypeBinding
import com.example.pokedex.type.ui.main.SectionsPagerAdapter
import com.example.pokedex.type.ui.main.TypeActivitySharedViewModel

lateinit var binding: ActivityPokemonTypeBinding

class PokemonTypeActivity : AppCompatActivity() {

    private val typeActivitySharedViewModel: TypeActivitySharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.backArrow.setOnClickListener {
            finish()
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        val url = intent.getStringExtra("url")!!

        var bundle = Bundle()
        bundle.putString("url", url)


        if(isNetworkConnected()) {
            typeActivitySharedViewModel.getType(url)

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