package com.mydrinksapp.ui.fragments.allingredients.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mydrinksapp.databinding.ItemIngredientByNameBinding
import com.mydrinksapp.model.data.Ingredient

class IngredientSelectedAdapter :
    RecyclerView.Adapter<IngredientSelectedAdapter.VieHolder>() {

    private var ingredientsByNameList = listOf<Ingredient>()

    fun setIngredientsByNameList(ingredientsByNameList: List<Ingredient>) {
        this.ingredientsByNameList = ingredientsByNameList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolder {
        val itemBinding =
            ItemIngredientByNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VieHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VieHolder, position: Int) {
        holder.setData(ingredientsByNameList[position])
    }

    override fun getItemCount(): Int = ingredientsByNameList.size

    inner class VieHolder(private val binding: ItemIngredientByNameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun setData(ingredient: Ingredient) {
            binding.txtIngredientsName.text = ingredient.name

            if (ingredient.description == null) {
                binding.txtIngredientsDescription.text = "(No data)"
            } else {
                binding.txtIngredientsDescription.text = ingredient.description
            }
        }
    }
}