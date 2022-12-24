package com.mydrinksapp.ui.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mydrinksapp.R
import com.mydrinksapp.base.BaseViewHolder
import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.data.model.asDrinkEntity

class FavoritesAdapter(
    private val context: Context,
    private val itemClickLister: OnCocktailClickListener
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var cocktailList = listOf<Drink>()

    interface OnCocktailClickListener {
        fun onCocktailClick(drink: Drink, position: Int)
        fun onCocktailDeleteLongClick(drink: DrinkEntity, position: Int)
    }

    fun setCocktailList(cocktailList: List<Drink>) {
        this.cocktailList = cocktailList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.tragos_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cocktailList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(cocktailList[position], position)
        }
    }

    private inner class MainViewHolder(itemView: View) : BaseViewHolder<Drink>(itemView) {
        override fun bind(item: Drink, position: Int) {
            Glide.with(context).load(item.image).centerCrop()
                .into(itemView.findViewById(R.id.img_cocktail))
            itemView.findViewById<TextView>(R.id.txt_titulo).text = item.name
            itemView.findViewById<TextView>(R.id.txt_descripcion).text = item.description

            itemView.setOnLongClickListener {
                itemClickLister.onCocktailDeleteLongClick(item.asDrinkEntity(), position)
                return@setOnLongClickListener true
            }

            itemView.setOnClickListener {
                itemClickLister.onCocktailClick(item, position)
            }
        }
    }
}