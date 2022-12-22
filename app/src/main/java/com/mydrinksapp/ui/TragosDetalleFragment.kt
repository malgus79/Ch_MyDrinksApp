package com.mydrinksapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mydrinksapp.R
import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.databinding.FragmentTragosDetalleBinding

class TragosDetalleFragment : Fragment() {

    private lateinit var binding: FragmentTragosDetalleBinding
    private lateinit var drink: Drink

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            drink = it.getParcelable<Drink>("drink")!!

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tragos_detalle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTragosDetalleBinding.bind(view)
        Glide.with(requireContext())
            .load(drink.imagen)
            .centerCrop()
            .into(binding.imgTrago)
        binding.tragoTitle.text = drink.nombre
        binding.tragoDesc.text = drink.descripcion
        if (drink.hasAlcohol.equals("Non_Alcoholic")) {
            binding.txtHasAlcohol.text = "Bebida sin alcohol"
        } else {
            binding.txtHasAlcohol.text = "Bebida con alcohol"
        }
    }
}