package com.mydrinksapp.ui.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mydrinksapp.base.BaseViewHolder
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.data.model.FavoritesEntity
import com.mydrinksapp.data.model.asFavoriteEntity
import com.mydrinksapp.databinding.TragosRowBinding

class FavoritesAdapter(
    private val context: Context,
    private val itemClickLister: OnCocktailClickListener
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var cocktailList = listOf<Cocktail>()

    interface OnCocktailClickListener {
        fun onCocktailClick(cocktail: Cocktail, position: Int)
        fun onCocktailDeleteLongClick(favorites: FavoritesEntity, position: Int)
    }

    fun setCocktailList(cocktailList: List<Cocktail>) {
        this.cocktailList = cocktailList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = TragosRowBinding.inflate(LayoutInflater.from(context), parent, false)
//        return MainViewHolder(itemBinding)

        val vh = MainViewHolder(itemBinding)

        vh.itemView.setOnClickListener {
            val pos = vh.adapterPosition
            if (pos != DiffUtil.DiffResult.NO_POSITION) {
                itemClickLister.onCocktailClick(cocktailList[pos], pos)
            }
        }

        vh.itemView.setOnLongClickListener {
            val pos = vh.adapterPosition
            if (pos != DiffUtil.DiffResult.NO_POSITION) {
                itemClickLister.onCocktailDeleteLongClick(cocktailList[pos].asFavoriteEntity(), pos)
            }
            return@setOnLongClickListener true
        }

        return vh
    }

    override fun getItemCount(): Int {
        return cocktailList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(cocktailList[position], position)
        }
    }

    private inner class MainViewHolder(private val binding: TragosRowBinding) : BaseViewHolder<Cocktail>(binding.root) {
        override fun bind(item: Cocktail, position: Int) = with(binding) {
            Glide.with(context).load(item.image).centerCrop().into(imgCocktail)
            txtTitulo.text = item.name
            txtDescripcion.text = item.description
        }
    }
}