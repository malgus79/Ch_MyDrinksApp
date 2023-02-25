package com.mydrinksapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mydrinksapp.databinding.ItemCategoriesInHomeBinding
import com.mydrinksapp.model.data.Categories

class CategoriesListAdapter :
    RecyclerView.Adapter<CategoriesListAdapter.VieHolder>() {

    private var categoriesList = listOf<Categories>()

    fun setCategoriesList(categoriesList: List<Categories>) {
        this.categoriesList = categoriesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolder {
        val itemBinding =
            ItemCategoriesInHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return VieHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VieHolder, position: Int) {
        holder.setData(categoriesList[position])
    }

    override fun getItemCount(): Int = categoriesList.size

    inner class VieHolder(private val binding: ItemCategoriesInHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(item: Categories) {
            binding.txtTitleCategories.text = item.category

            binding.mcvContainer.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToCategoriesFragment(
                        item.category.toString()
                    )
                this.itemView.findNavController().navigate(action)
            }
        }
    }
}