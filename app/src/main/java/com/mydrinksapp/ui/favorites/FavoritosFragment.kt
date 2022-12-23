package com.mydrinksapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mydrinksapp.R
import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.data.model.DrinkEntity
import com.mydrinksapp.databinding.FragmentFavoritosBinding
import com.mydrinksapp.ui.viewmodel.MainViewModel
import com.mydrinksapp.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritosFragment : Fragment(), FavoritesAdapter.OnCocktailClickListener {

    private lateinit var binding: FragmentFavoritosBinding
    private lateinit var favoritesAdapter:FavoritesAdapter
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesAdapter = FavoritesAdapter(requireContext(),this)
    }

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
                    favoritesAdapter.setCocktailList(result.data)
                }
                is Resource.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "An error ocurred ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvTragosFavoritos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTragosFavoritos.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
        binding.rvTragosFavoritos.adapter = favoritesAdapter
    }

    override fun onCocktailClick(drink: Drink, position: Int) {
        val bundle = Bundle()
        bundle.putParcelable("drink", drink)
        findNavController().navigate(R.id.action_favoritosFragment_to_tragosDetalleFragment, bundle)
    }

    override fun onCocktailDeleteLongClick(drink: DrinkEntity, position: Int) {
        viewModel.deleteDrink(drink).observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> { }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Drink deleted !", Toast.LENGTH_SHORT).show()
                    favoritesAdapter.setCocktailList(result.data)
                }
                is Resource.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "An error occurred ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}