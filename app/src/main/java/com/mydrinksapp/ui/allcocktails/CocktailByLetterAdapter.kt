package com.mydrinksapp.ui.allcocktails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mydrinksapp.R
import com.mydrinksapp.databinding.ItemCocktailsByLetterBinding
import com.mydrinksapp.model.data.Cocktail

class CocktailByLetterAdapter : RecyclerView.Adapter<CocktailByLetterAdapter.VieHolder>() {

    private var cocktailByLetterList = listOf<Cocktail>()

    fun setCocktailByLetterList(cocktailByLetterList: List<Cocktail>) {
        this.cocktailByLetterList = cocktailByLetterList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolder {
        val itemBinding =
            ItemCocktailsByLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VieHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VieHolder, position: Int) {
        holder.setData(cocktailByLetterList[position])
    }

    override fun getItemCount(): Int = cocktailByLetterList.size

    inner class VieHolder(private val binding: ItemCocktailsByLetterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(cocktail: Cocktail) {
            Glide.with(binding.root.context)
                .load(cocktail.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.gradient)
                .centerCrop()
                .into(binding.imgCocktail)

            binding.txtTitle.text = cocktail.name
            binding.txtDescription.text = cocktail.description

            binding.mcvCocktail.setOnClickListener {
                val action =
                    AllCocktailsFragmentDirections.actionAllCocktailsFragmentToDetailByLetterFragment(
                        cocktail.cocktailId
                    )
                this.itemView.findNavController().navigate(action)
            }
        }
    }
}