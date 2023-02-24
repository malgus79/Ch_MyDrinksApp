package com.mydrinksapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mydrinksapp.databinding.ItemCategoriesBinding
import com.mydrinksapp.model.data.Categories

class CategoriesAdapter :
    RecyclerView.Adapter<CategoriesAdapter.VieHolder>() {

    private var categoriesList = listOf<Categories>()

    fun setCategoriesList(categoriesList: List<Categories>) {
        this.categoriesList = categoriesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolder {
        val itemBinding =
            ItemCategoriesBinding.inflate(
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

    inner class VieHolder(private val binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(item: Categories) {
            binding.txtTitleCategories.text = item.category

//            binding.mcvContainer.setOnClickListener {
//                val action =
//                    HomeFragmentDirections.actionHomeFragmentToDetailByLetterFragment(
//                        item.category.toString()
//                    )
//                this.itemView.findNavController().navigate(action)
//            }
        }
    }
}