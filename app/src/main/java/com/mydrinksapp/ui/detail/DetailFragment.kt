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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
//    private val args by navArgs<FavoritesFragmentArgs>()

    private lateinit var cocktail: Cocktail
    private var isCocktailFavorited: Boolean? = null

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
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        Glide.with(requireContext())
            .load(cocktail.image)
            .centerCrop()
            .into(binding.imgCocktail)
        binding.cocktailTitle.text = cocktail.name
        binding.cocktailDesc.text = cocktail.description

//        binding.btnSaveCocktail.setOnClickListener {
//            viewModel.saveCocktail(
//                FavoritesEntity(
//                    cocktail.cocktailId,
//                    cocktail.image,
//                    cocktail.name,
//                    cocktail.description,
//                    cocktail.hasAlcohol
//                )
//            )
//            Toast.makeText(requireContext(), "Se guardó el trago en favoritos", Toast.LENGTH_SHORT)
//                .show()
//        }

        fun updateButtonIcon() {
            val isCocktailFavorited = isCocktailFavorited ?: return

            binding.btnSaveOrDeleteCocktail.setImageResource(
                when {
                    isCocktailFavorited -> R.drawable.ic_baseline_delete_24
                    else -> R.drawable.ic_baseline_save_24
                }
            )
        }

        binding.btnSaveOrDeleteCocktail.setOnClickListener {
            val isCocktailFavorited = isCocktailFavorited ?: return@setOnClickListener

            viewModel.saveOrDeleteFavoriteCocktail(cocktail)
            this.isCocktailFavorited = !isCocktailFavorited
            updateButtonIcon()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            isCocktailFavorited = viewModel.isCocktailFavorite(cocktail)
            updateButtonIcon()
        }
    }
}