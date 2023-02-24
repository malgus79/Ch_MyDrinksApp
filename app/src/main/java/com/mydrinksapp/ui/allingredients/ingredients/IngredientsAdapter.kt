package com.mydrinksapp.ui.allingredients.ingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mydrinksapp.databinding.ItemAllIngredientsBinding

class IngredientsAdapter(
    private var ingredientsMutableList: MutableList<Ingredients>,
    private val itemClickListener: OnIngredientsClickListener
) : RecyclerView.Adapter<IngredientsAdapter.VieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolder {
        val itemBinding =
            ItemAllIngredientsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return VieHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VieHolder, position: Int) {
        holder.setData(ingredientsMutableList[position])
    }

    override fun getItemCount(): Int = ingredientsMutableList.size

    inner class VieHolder(private val binding: ItemAllIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(ingredients: Ingredients) {
            binding.btnIngredients.text = ingredients.ingredients

            binding.btnIngredients.setOnClickListener {
                itemClickListener.onIngredientsClick(ingredients.ingredients)
            }
        }
    }

    interface OnIngredientsClickListener {
        fun onIngredientsClick(ingredients: String)
    }
}