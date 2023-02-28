package com.mydrinksapp.ui.fragments.allingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mydrinksapp.R
import com.mydrinksapp.base.Resource
import com.mydrinksapp.base.utils.hide
import com.mydrinksapp.base.utils.show
import com.mydrinksapp.base.utils.showToast
import com.mydrinksapp.databinding.FragmentIngredientsBinding
import com.mydrinksapp.model.data.AllIngredient
import com.mydrinksapp.ui.fragments.allingredients.adapter.AllIngredientsAdapter
import com.mydrinksapp.ui.fragments.allingredients.adapter.CocktailByIngredientsAdapter
import com.mydrinksapp.ui.fragments.allingredients.adapter.IngredientSelectedAdapter
import com.mydrinksapp.viewmodel.fragments.IngredientsViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.LandingAnimator

@AndroidEntryPoint
class IngredientsFragment : Fragment(), AllIngredientsAdapter.OnIngredientsClickListener {

    private lateinit var binding: FragmentIngredientsBinding

    private var ingredientsMutableList: MutableList<AllIngredient> = mutableListOf()

    private var adapterAllIngredients: AllIngredientsAdapter =
        AllIngredientsAdapter(ingredientsMutableList, this)

    private var adapterCocktailByIngredients: CocktailByIngredientsAdapter =
        CocktailByIngredientsAdapter()

    private var adapterIngredientSelected: IngredientSelectedAdapter = IngredientSelectedAdapter()

    private val viewModel: IngredientsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        setupAllIngredient()
        setupAllIngredientList()
        showIngredientSelected()

        return binding.root
    }

    private fun setupAllIngredient() {
        viewModel.fetchAllIngredients().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                    binding.rvCocktailsByIngredients.hide()
                }
                is Resource.Success -> {
                    binding.progressBar.hide()

                    setupCocktailsByIngredientsRecyclerView()
                    adapterCocktailByIngredients.setCocktailByIngredientsList(it.data.first.drinks)

                    showIngredientSelected()
                    adapterIngredientSelected.setIngredientsByNameList(it.data.second.ingredients)

                }
                is Resource.Failure -> {
                    binding.progressBar.hide()
                    showToast(getString(R.string.error_detail))
                }
            }
        }
    }

    private fun setupAllIngredientList() {
        viewModel.fetchAllIngredientList().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                    binding.rvAllIngredients.hide()
                }
                is Resource.Success -> {
                    binding.progressBar.hide()
                    if (it.data.drinks.isEmpty()) {
                        binding.rvAllIngredients.hide()
                        return@observe
                    }
                    setupIngredientsRecyclerView()
                    adapterAllIngredients.setAllIngredientList(it.data.drinks.toMutableList())
                }
                is Resource.Failure -> {
                    binding.progressBar.hide()
                    showToast(getString(R.string.error_detail))
                }
            }
        }
    }

    private fun setupIngredientsRecyclerView() {
        binding.rvAllIngredients.apply {
            adapter = adapterAllIngredients
            layoutManager =StaggeredGridLayoutManager(
                resources.getInteger(R.integer.columns_all_ingredients),
                StaggeredGridLayoutManager.VERTICAL
            )
            itemAnimator = LandingAnimator().apply { addDuration = 300 }
            setHasFixedSize(true)
            show()
        }
    }

    override fun onIngredientsClick(ingredients: String) {
        viewModel.setAllIngredients(ingredients)
        setupAllIngredient()
    }

    private fun setupCocktailsByIngredientsRecyclerView() {
        binding.rvCocktailsByIngredients.apply {
            adapter = adapterCocktailByIngredients
            layoutManager = StaggeredGridLayoutManager(
                resources.getInteger(R.integer.columns_cocktails_by_ingredients),
                StaggeredGridLayoutManager.VERTICAL
            )
            itemAnimator = LandingAnimator().apply { addDuration = 300 }
            setHasFixedSize(true)
            show()
        }
    }

    private fun showIngredientSelected() {
        binding.rvIngredientSelected.apply {
            adapter = adapterIngredientSelected
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            itemAnimator = LandingAnimator().apply { addDuration = 300 }
            setHasFixedSize(true)
            show()
        }
    }
}