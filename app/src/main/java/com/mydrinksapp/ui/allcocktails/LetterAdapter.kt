package com.mydrinksapp.ui.allcocktails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mydrinksapp.databinding.ItemAllCocktailsBinding

class LetterAdapter(private var letterMutableList: MutableList<Letter>) : RecyclerView.Adapter<LetterAdapter.VieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolder {
        val itemBinding =
            ItemAllCocktailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return VieHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VieHolder, position: Int) {
        holder.setData(letterMutableList[position])
    }

    override fun getItemCount(): Int = letterMutableList.size

    inner class VieHolder(private val binding: ItemAllCocktailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(item: Letter) {
            binding.txtLetter.text = item.letter

            //TODO conectar con detalle
//            binding.mcvCocktail.setOnClickListener {
//                val action =
//                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(
//                        cocktailPopular.cocktailId.toString()
//                    )
//                this.itemView.findNavController().navigate(action)
//            }
        }
    }
}