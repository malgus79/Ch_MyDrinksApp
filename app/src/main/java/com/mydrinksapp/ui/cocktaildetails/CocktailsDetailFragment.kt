package com.mydrinksapp.ui.cocktaildetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.mydrinksapp.R
import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.databinding.FragmentTragosDetalleBinding
import com.mydrinksapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailsDetailFragment : Fragment() {

    private lateinit var binding: FragmentTragosDetalleBinding
    private lateinit var drink: Drink
    private val viewModel by activityViewModels<MainViewModel>()

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
            .load(drink.image)
            .centerCrop()
            .into(binding.imgCocktail)
        binding.cocktailTitle.text = drink.name
        binding.cocktailDesc.text = drink.description

        binding.btnSaveCocktail.setOnClickListener {
            viewModel.saveCocktail(
                DrinkEntity(
                    drink.cocktailId,
                    drink.image,
                    drink.name,
                    drink.description,
                    drink.hasAlcohol
                )
            )
            Toast.makeText(requireContext(), "Se guardó el trago en favoritos", Toast.LENGTH_SHORT)
                .show()
        }
    }
}