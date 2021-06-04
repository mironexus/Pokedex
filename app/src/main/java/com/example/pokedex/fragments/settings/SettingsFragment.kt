package com.example.pokedex.fragments.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentSettingsBinding
import com.example.pokedex.fragments.favorites.FavoritesViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val favoritesViewModel: FavoritesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        val items = listOf("English", "German", "French", "Croatian")
        val adapter = ArrayAdapter(requireContext(), R.layout.language_menu_item, items)
        binding.languageMenu.setAdapter(adapter)


        binding.settingsMoreInfo.setOnClickListener {
            val intent = Intent(context, AboutActivity::class.java)
            context?.startActivity(intent)
        }


        binding.clearButton.setOnClickListener{

            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle(resources.getString(R.string.settings_clear))
                    .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                    .setPositiveButton("Clear") { dialog, _ -> favoritesViewModel.deleteAllFavorites()
                    dialog.dismiss() }.show()
            }

        }

        return view
    }
}