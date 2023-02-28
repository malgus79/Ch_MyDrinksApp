package com.mydrinksapp.ui.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mydrinksapp.R
import com.mydrinksapp.base.Resource
import com.mydrinksapp.base.utils.show
import com.mydrinksapp.base.utils.showToast
import com.mydrinksapp.databinding.FragmentFavoriteBinding
import com.mydrinksapp.model.data.Cocktail
import com.mydrinksapp.ui.fragments.favorites.adapter.FavoritesAdapter
import com.mydrinksapp.viewmodel.fragments.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.LandingAnimator

@AndroidEntryPoint
class FavoritesFragment : Fragment(), FavoritesAdapter.OnCocktailClickListener {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var adapterFavorites: FavoritesAdapter
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapterFavorites = FavoritesAdapter(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        setupRecyclerView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.getFavoriteCocktails().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (result.data.isEmpty()) {
                        binding.emptyContainer.root.visibility = View.VISIBLE
                        return@Observer
                    }
                    adapterFavorites.setCocktailList(result.data)
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
        binding.rvFavoriteCocktail.apply {
            adapter = adapterFavorites
            layoutManager = StaggeredGridLayoutManager(
                resources.getInteger(R.integer.columns_favorite),
                StaggeredGridLayoutManager.VERTICAL
            )
            itemAnimator = LandingAnimator().apply { addDuration = 300 }
            setHasFixedSize(true)
            show()
        }
    }

    override fun onCocktailClick(cocktail: Cocktail, position: Int) {
        findNavController().navigate(
            FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(
                cocktail
            )
        )
    }

    override fun onCocktailLongClick(cocktail: Cocktail, position: Int) {
        viewModel.deleteFavoriteCocktail(cocktail)
        showToast(getString(R.string.removed_cocktail))
    }
}