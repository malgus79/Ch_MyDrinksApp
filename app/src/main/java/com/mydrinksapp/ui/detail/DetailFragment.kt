package com.mydrinksapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mydrinksapp.R
import com.mydrinksapp.databinding.FragmentDetailBinding
import com.mydrinksapp.model.data.Cocktail
import com.mydrinksapp.ui.viewmodel.DetailViewModel
import com.mydrinksapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    private lateinit var cocktail: Cocktail
    private var isFavoriteCocktail: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().let {
            DetailFragmentArgs.fromBundle(it).also { args ->
                cocktail = args.drink
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        setupShowCocktailDetails()
        isFavoriteCocktails()

        return binding.root
    }

    private fun setupShowCocktailDetails() {
        Glide.with(requireContext())
            .load(cocktail.image)
            .centerCrop()
            .into(binding.imgCocktail)
        binding.cocktailTitle.text = cocktail.name
        binding.cocktailDesc.text = cocktail.description
    }

    private fun isFavoriteCocktails() {
        binding.btnSaveOrDeleteCocktail.setOnClickListener {
            val isFavoriteCocktail = isFavoriteCocktail ?: return@setOnClickListener

            if (isFavoriteCocktail) {
                showToast(getString(R.string.removed_cocktail))
            } else {
                showToast(getString(R.string.added_cocktail))
            }
            viewModel.saveOrDeleteFavoriteCocktail(cocktail)
            this.isFavoriteCocktail = !isFavoriteCocktail
            updateButtonIcon()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            isFavoriteCocktail = viewModel.isCocktailFavorite(cocktail)
            updateButtonIcon()
        }
    }

    private fun updateButtonIcon() {
        val isFavoriteCocktail = isFavoriteCocktail ?: return

        binding.btnSaveOrDeleteCocktail.setImageResource(
            when {
                isFavoriteCocktail -> R.drawable.ic_delete
                else -> R.drawable.ic_add
            }
        )
    }
}
