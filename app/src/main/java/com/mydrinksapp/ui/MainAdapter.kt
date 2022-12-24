package com.mydrinksapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mydrinksapp.R
import com.mydrinksapp.base.BaseViewHolder
import com.mydrinksapp.data.model.Cocktail

class MainAdapter(
    private val context: Context,
    private val itemClickLister: OnTragoClickListener
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var cocktailList = listOf<Cocktail>()

    interface OnTragoClickListener {
        fun onCocktailClick(cocktail: Cocktail, position: Int)
    }

    fun setCocktailList(cocktailList: List<Cocktail>) {
        this.cocktailList = cocktailList
        notifyDataSetChanged()
    }

    //retornar una lista, la inner class (que se va a inflar ?)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.tragos_row, parent, false)
        )
    }

    //bindear los datos (cual es el holder que se va a bindear ? )
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(cocktailList[position], position)
        }
    }

    //tamaño de la lista
    override fun getItemCount(): Int {
        return cocktailList.size
    }

    private inner class MainViewHolder(itemView: View) : BaseViewHolder<Cocktail>(itemView) {
        override fun bind(item: Cocktail, position: Int) {
            Glide.with(context).load(item.image)
                .centerCrop()
                .into(itemView.findViewById(R.id.img_cocktail))
            itemView.findViewById<TextView>(R.id.txt_titulo).text = item.name
            itemView.findViewById<TextView>(R.id.txt_descripcion).text = item.description
            itemView.setOnClickListener { itemClickLister.onCocktailClick(item, position) }
        }
    }
}