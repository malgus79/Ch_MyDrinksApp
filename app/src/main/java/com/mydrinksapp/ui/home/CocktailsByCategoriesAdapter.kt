package com.mydrinksapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mydrinksapp.R
import com.mydrinksapp.databinding.ItemCocktailsByCategoriesBinding
import com.mydrinksapp.model.data.CocktailByCategory

class CocktailsByCategoriesAdapter :
    RecyclerView.Adapter<CocktailsByCategoriesAdapter.VieHolder>() {

    private var cocktailsByCategoriesList = listOf<CocktailByCategory>()

    fun setCocktailsByCategoriesList(cocktailsByCategoriesList: List<CocktailByCategory>) {
        this.cocktailsByCategoriesList = cocktailsByCategoriesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolder {
        val itemBinding =
            ItemCocktailsByCategoriesBinding.inflate(
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

    inner class VieHolder(private val binding: ItemCocktailsByCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(item: CocktailByCategory) {
            Glide.with(binding.root.context)
                .load(item.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.gradient)
                .centerCrop()
                .into(binding.imgCocktail)

            binding.txtTitle.text = item.name

            binding.mcvCocktail.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToDetailByLetterFragment(
                        item.cocktailId.toString()
                    )
                this.itemView.findNavController().navigate(action)
            }
        }
    }
}