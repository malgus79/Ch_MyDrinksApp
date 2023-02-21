package com.mydrinksapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mydrinksapp.R
import com.mydrinksapp.databinding.ItemPopularBinding
import com.mydrinksapp.model.data.CocktailByCategory

class PopularAdapter : RecyclerView.Adapter<PopularAdapter.VieHolder>() {

    private var cocktailPopularList = listOf<CocktailByCategory>()

    fun setCocktailPopularList(cocktailPopularList: List<CocktailByCategory>) {
        this.cocktailPopularList = cocktailPopularList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolder {
        val itemBinding =
            ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VieHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: VieHolder, position: Int) {
        holder.setData(cocktailPopularList[position])
    }

    override fun getItemCount(): Int = cocktailPopularList.size

    inner class VieHolder(private val binding: ItemPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(cocktailPopular: CocktailByCategory) {
            Glide.with(binding.root.context)
                .load(cocktailPopular.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.gradient)
                .centerCrop()
                .into(binding.imgCocktail)

            binding.txtTitle.text = cocktailPopular.name

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