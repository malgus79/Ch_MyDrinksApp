package com.mydrinksapp.ui.allingredients.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mydrinksapp.databinding.ItemAllIngredientsBinding
import com.mydrinksapp.data.model.AllIngredient

class AllIngredientsAdapter(
    private var ingredientsList: MutableList<AllIngredient>,
    private val itemClickListener: OnIngredientsClickListener
) : RecyclerView.Adapter<AllIngredientsAdapter.VieHolder>() {

    fun setAllIngredientList(ingredient: MutableList<AllIngredient>) {
        this.ingredientsList = ingredient
        ingredientsList.sortBy { it.ingredientName }
    }


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
        holder.setData(ingredientsList[position])
    }

    override fun getItemCount(): Int = ingredientsList.size

    inner class VieHolder(private val binding: ItemAllIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(ingredient: AllIngredient) {
            binding.btnIngredients.text = ingredient.ingredientName

            binding.btnIngredients.setOnClickListener {
                itemClickListener.onIngredientsClick(ingredient.ingredientName.toString())
            }
        }
    }

    interface OnIngredientsClickListener {
        fun onIngredientsClick(ingredients: String)
    }
}