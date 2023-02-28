package com.mydrinksapp.ui.fragments.allcocktails.letter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mydrinksapp.databinding.ItemAllCocktailsBinding

class LetterAdapter(
    private var letterMutableList: MutableList<Letter>,
    private val itemClickListener: OnLetterClickListener
) : RecyclerView.Adapter<LetterAdapter.VieHolder>() {

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

        fun setData(letter: Letter) {
            binding.btnLetter.text = letter.letter

            binding.btnLetter.setOnClickListener {
                itemClickListener.onLetterClick(letter.letter)
            }
        }
    }

    interface OnLetterClickListener {
        fun onLetterClick(letter: String)
    }
}