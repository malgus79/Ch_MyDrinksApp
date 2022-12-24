package com.mydrinksapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mydrinksapp.base.BaseViewHolder
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.databinding.TragosRowBinding

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
        val itemBinding = TragosRowBinding.inflate(LayoutInflater.from(context), parent, false)
//        return MainViewHolder(itemBinding)

        val vh = MainViewHolder(itemBinding)
        vh.itemView.setOnClickListener {
            val pos = vh.adapterPosition
            if (pos != DiffUtil.DiffResult.NO_POSITION) {
                itemClickLister.onCocktailClick(cocktailList[pos], pos)
            }
        }
        return vh
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

    private inner class MainViewHolder(val binding: TragosRowBinding) : BaseViewHolder<Cocktail>(binding.root) {
        override fun bind(item: Cocktail, position: Int) = with(binding) {
            Glide.with(context).load(item.image).centerCrop().into(imgCocktail)
            txtTitulo.text = item.name
            txtDescripcion.text = item.description
        }
    }
}