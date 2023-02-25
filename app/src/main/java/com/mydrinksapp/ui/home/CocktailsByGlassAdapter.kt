package com.mydrinksapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mydrinksapp.R
import com.mydrinksapp.databinding.ItemCocktailsByCategoriesInHomeBinding
import com.mydrinksapp.model.data.CocktailByCategory

class CocktailsByGlassAdapter :
    RecyclerView.Adapter<CocktailsByGlassAdapter.VieHolder>() {

    private var cocktailsByGlassList = listOf<CocktailByCategory>()

    fun setCocktailsByGlassList(cocktailsByGlassList: List<CocktailByCategory>) {
        this.cocktailsByGlassList = cocktailsByGlassList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolder {
        val itemBinding =
            ItemCocktailsByCategoriesInHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return VieHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VieHolder, position: Int) {
        holder.setData(cocktailsByGlassList[position])
    }

    override fun getItemCount(): Int = cocktailsByGlassList.size

    inner class VieHolder(private val binding: ItemCocktailsByCategoriesInHomeBinding) :
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