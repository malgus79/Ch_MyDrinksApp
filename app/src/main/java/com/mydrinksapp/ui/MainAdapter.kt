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
import com.mydrinksapp.data.model.Drink

class MainAdapter(
    private val context: Context,
    private val tragosList: List<Drink>,
    private val itemClickListener: OnTragoClickListener
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnTragoClickListener {
        fun onTragoClick(drink: Drink, position: Int)
    }

//    fun deleteDrink(position: Int){
//        tragosList.removeAt(position)
//        notifyDataSetChanged()
//        notifyItemRemoved(position)
//    }

    //retornar una lista, la inner class (que se va a inflar ?)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.tragos_row, parent, false)
        )
    }

    //bindear los datos (cual es el holder que se va a bindear ? )
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(tragosList[position], position)
        }
    }

    //tamaño de la lista
    override fun getItemCount(): Int {
        return tragosList.size
    }

    inner class MainViewHolder(itemView: View) : BaseViewHolder<Drink>(itemView) {
        override fun bind(item: Drink, position: Int) {
            Glide.with(context).load(item.imagen)
                .centerCrop()
                .into(itemView.findViewById(R.id.img_trago))
            itemView.findViewById<TextView>(R.id.txt_titulo).text = item.nombre
            itemView.findViewById<TextView>(R.id.txt_descripcion).text = item.descripcion
            itemView.setOnClickListener { itemClickListener.onTragoClick(item, position) }
        }
    }
}