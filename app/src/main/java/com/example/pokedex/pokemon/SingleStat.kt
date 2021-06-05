package com.example.pokedex.pokemon

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pokedex.R
import com.example.pokedex.databinding.SingleStatBinding

class SingleStat(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding: SingleStatBinding

    init {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = SingleStatBinding.inflate(layoutInflater)
        FrameLayout.inflate(context, R.layout.single_stat, this)
        addView(binding.root)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SingleStat,
            0, 0
        ).apply {
            try {
                val stat = getString(R.styleable.SingleStat_label)
                val value = getString(R.styleable.SingleStat_value)
                val progress = getString(R.styleable.SingleStat_progress)
                val stat_color = getResourceId(R.styleable.SingleStat_stat_color, 0)

                binding.statLabel.text = stat
                binding.statValue.text = value
                if (progress != null) {
                    binding.progressBar.progress = progress.toInt()
                }
                binding.progressBar.progressDrawable.setTint(stat_color.toInt())

            } finally {
                recycle()
            }
        }
    }

        fun setStatLabel(value: String) {
            binding.statLabel.text = value
        }

        fun setStatValue(value: String) {
            binding.statValue.text = value
        }

        fun setProgress(value: String) {
            binding.progressBar.progress = value.toInt()
        }
        fun setColor(value: Int) {
            binding.progressBar.progressDrawable.setTint(value)
        }

}