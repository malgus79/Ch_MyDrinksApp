package com.mydrinksapp.ui.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mydrinksapp.R
import com.mydrinksapp.databinding.ItemCocktailsByCategoryInCategoriesBinding
import com.mydrinksapp.data.model.CocktailByCategory
import com.mydrinksapp.ui.categories.CategoriesFragmentDirections

class CategoriesAdapter :
    RecyclerView.Adapter<CategoriesAdapter.VieHolder>() {

    private var cocktailsByCategoriesList = listOf<CocktailByCategory>()

    fun setCocktailsByCategories(cocktailsByCategoriesList: List<CocktailByCategory>) {
        this.cocktailsByCategoriesList = cocktailsByCategoriesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolder {
        val itemBinding =
            ItemCocktailsByCategoryInCategoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return VieHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VieHolder, position: Int) {
        holder.setData(cocktailsByCategoriesList[position])
    }

    override fun getItemCount(): Int = cocktailsByCategoriesList.size

    inner class VieHolder(private val binding: ItemCocktailsByCategoryInCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(item: CocktailByCategory) {
            Glide.with(binding.root.context)
                .load(item.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.gradient)
                .centerCrop()
                .into(binding.imgCocktailsByCategories)

            binding.txtCocktailsByCategoryName.text = item.name

            binding.mcvContainerItemCocktailsByCategory.setOnClickListener {
                val action =
                    CategoriesFragmentDirections.actionCategoriesFragmentToDetailByLetterFragment(
                        item.cocktailId.toString()
                    )
                this.itemView.findNavController().navigate(action)
            }
        }
    }
}