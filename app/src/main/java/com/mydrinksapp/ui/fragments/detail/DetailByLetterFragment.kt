package com.mydrinksapp.ui.fragments.detail

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mydrinksapp.R
import com.mydrinksapp.base.utils.showToast
import com.mydrinksapp.databinding.FragmentDetailByLetterBinding
import com.mydrinksapp.model.data.Cocktail
import com.mydrinksapp.viewmodel.fragments.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailByLetterFragment : Fragment() {

    private lateinit var binding: FragmentDetailByLetterBinding
    private val args by navArgs<DetailByLetterFragmentArgs>()
    private val viewModel: DetailViewModel by viewModels()

    private lateinit var cocktail: Cocktail
    private var isFavoriteCocktail: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailByLetterBinding.inflate(inflater, container, false)
        cocktail = Cocktail()

        isLoading(true)
        setupShowCocktailDetails()
        onClickShareCocktails()

        return binding.root
    }

    private fun setupShowCocktailDetails() {
        viewModel.fetchCocktailDetailsById(args.idDrink)
        viewModel.cocktailByLetterDetailMutableLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                cocktail = it
                loadData(it)
                isFavoriteCocktail()
                updateButtonIcon()
            }
        }
    }

    private fun loadData(cocktail: Cocktail) {
        try {
            Glide.with(this@DetailByLetterFragment)
                .load(cocktail.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.gradient)
                .centerCrop()
                .into(binding.imgCocktail)

            binding.cocktailTitle.text = cocktail.name
            binding.cocktailDesc.text = cocktail.description

            isLoading(false)
        } catch (e: Exception) {
            showToast("${e.message}")
        }
    }

    private fun isLoading(loading: Boolean) {
        binding.progressBar?.isVisible = loading
        binding.btnShareCocktail.isVisible = !loading
        binding.btnSaveOrDeleteCocktail.isVisible = !loading
        binding.cocktailTitle.isVisible = !loading
        binding.cocktailDesc.isVisible = !loading
    }

    private fun isFavoriteCocktail() {
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

    private fun onClickShareCocktails() {
        binding.btnShareCocktail.setOnClickListener {
            try {
                val bitmapDrawable = binding.imgCocktail.drawable as BitmapDrawable
                val bitmap = bitmapDrawable.bitmap
                val bitmapPath =
                    MediaStore.Images.Media.insertImage(
                        context?.contentResolver,
                        bitmap,
                        "IMAGE" + System.currentTimeMillis(),
                        null
                    )
                val bitmapUri = Uri.parse(bitmapPath.toString())
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
                startActivity(Intent.createChooser(intent, "My Meal App"))
            } catch (e: Exception) {
                showToast(getString(R.string.share_error))
            }
        }
    }
}