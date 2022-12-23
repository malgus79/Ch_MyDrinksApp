package com.mydrinksapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mydrinksapp.R
import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.databinding.FragmentFavoritosBinding
import com.mydrinksapp.ui.MainAdapter
import com.mydrinksapp.ui.viewmodel.MainViewModel
import com.mydrinksapp.utils.ext.asDrinkList
import com.mydrinksapp.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritosFragment : Fragment(), MainAdapter.OnTragoClickListener {

    private lateinit var binding: FragmentFavoritosBinding
    private lateinit var adapter: MainAdapter
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritosBinding.bind(view)

        setupRecyclerView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.getTragosFavoritos().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val lista = result.data.asDrinkList()
                    adapter = MainAdapter(requireContext(), lista, this)
                    binding.rvTragosFavoritos.adapter = adapter

                    //Log.d("LISTA DE FAVORITOS: ", "${result.data}")
                }
                is Resource.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "Ocurrió un error ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvTragosFavoritos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTragosFavoritos.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.HORIZONTAL
            )
        )
    }

    override fun onTragoClick(drink: Drink, position: Int) {
        viewModel.deleteDrink(DrinkEntity(drink.tragoId, drink.imagen, drink.nombre, drink.descripcion, drink.hasAlcohol))
        binding.rvTragosFavoritos.adapter?.notifyItemRemoved(position)
//        binding.rvTragosFavoritos.adapter?.notifyItemRangeRemoved(
//            position,
//            binding.rvTragosFavoritos.adapter?.itemCount!!
//        )

//        adapter.deleteDrink(position)
        Toast.makeText(requireContext(), "Se borró el trago de favorito", Toast.LENGTH_SHORT).show()
    }
}