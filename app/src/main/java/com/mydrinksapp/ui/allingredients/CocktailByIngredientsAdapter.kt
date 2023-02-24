package com.mydrinksapp.ui.allingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mydrinksapp.R
import com.mydrinksapp.databinding.ItemCocktailsByIngredientsBinding
import com.mydrinksapp.model.data.Cocktail

class CocktailByIngredientsAdapter :
    RecyclerView.Adapter<CocktailByIngredientsAdapter.VieHolder>() {

    private var cocktailByIngredientsList = listOf<Cocktail>()

    fun setCocktailByIngredientsList(cocktailByIngredientsList: List<Cocktail>) {
        this.cocktailByIngredientsList = cocktailByIngredientsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolder {
        val itemBinding =
            ItemCocktailsByIngredientsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return VieHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VieHolder, position: Int) {
        holder.setData(cocktailByIngredientsList[position])
    }

    override fun getItemCount(): Int = cocktailByIngredientsList.size

    inner class VieHolder(private val binding: ItemCocktailsByIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(cocktail: Cocktail) {
            Glide.with(binding.root.context)
                .load(cocktail.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.gradient)
                .centerCrop()
                .into(binding.imgCocktail)

            binding.txtTitleCocktails.text = cocktail.name

            binding.mcvContainer.setOnClickListener {
                val action =
                    IngredientsFragmentDirections.actionIngredientsFragmentToDetailByLetterFragment(
                        cocktail.cocktailId.toString()
                    )
                this.itemView.findNavController().navigate(action)
            }
        }
    }
}