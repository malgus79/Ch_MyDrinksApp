package com.mydrinksapp.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mydrinksapp.databinding.ItemSearchCocktailBinding
import com.mydrinksapp.model.data.Cocktail

class SearchAdapter(
    private val context: Context,
    private val itemClickLister: OnTragoClickListener
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var cocktailList = listOf<Cocktail>()

    interface OnTragoClickListener {
        fun onCocktailClick(cocktail: Cocktail, position: Int)
    }

    fun setCocktailList(cocktailList: List<Cocktail>) {
        this.cocktailList = cocktailList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemSearchCocktailBinding.inflate(LayoutInflater.from(context), parent, false)
//        return MainViewHolder(itemBinding)

        val vh = ViewHolder(itemBinding)
        vh.itemView.setOnClickListener {
            val pos = vh.adapterPosition
            if (pos != DiffUtil.DiffResult.NO_POSITION) {
                itemClickLister.onCocktailClick(cocktailList[pos], pos)
            }
        }
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(cocktailList[position])
    }

    override fun getItemCount(): Int {
        return cocktailList.size
    }

    inner class ViewHolder(private val binding: ItemSearchCocktailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: Cocktail) = with(binding) {
            Glide.with(context).load(item.image).centerCrop().into(imgCocktail)
            txtTitle.text = item.name
            txtDescription.text = item.description
        }
    }
}