package com.mydrinksapp.ui.favorites.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mydrinksapp.databinding.ItemFavoriteCocktailBinding
import com.mydrinksapp.data.model.Cocktail

class FavoritesAdapter(
    private val context: Context,
    private val itemClickListener: OnCocktailClickListener
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private var cocktailList = listOf<Cocktail>()

    interface OnCocktailClickListener {
        fun onCocktailClick(cocktail: Cocktail, position: Int)
        fun onCocktailLongClick(cocktail: Cocktail, position: Int)
    }

    fun setCocktailList(cocktailList: List<Cocktail>) {
        this.cocktailList = cocktailList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemFavoriteCocktailBinding.inflate(LayoutInflater.from(context), parent, false)
//        return MainViewHolder(itemBinding)

        val holder = ViewHolder(itemBinding)

        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != NO_POSITION }
                ?: return@setOnClickListener

            itemClickListener.onCocktailClick(cocktailList[position], position)
        }

        holder.itemView.setOnLongClickListener {
            val position = holder.adapterPosition.takeIf { it != NO_POSITION }
                ?: return@setOnLongClickListener true

            itemClickListener.onCocktailLongClick(cocktailList[position], position)

            return@setOnLongClickListener true
        }

        return holder
    }

    override fun getItemCount(): Int = cocktailList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(cocktailList[position])
    }

    inner class ViewHolder(private val binding: ItemFavoriteCocktailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: Cocktail) = with(binding) {
            Glide.with(context).load(item.image).centerCrop().into(imgFavoriteCocktail)
            txtFavoriteTitle.text = item.name
        }
    }
}