package com.mydrinksapp.ui.allingredients

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
import com.mydrinksapp.databinding.FragmentIngredientsBinding
import com.mydrinksapp.model.data.AllIngredient
import com.mydrinksapp.ui.viewmodel.IngredientsViewModel
import com.mydrinksapp.utils.hide
import com.mydrinksapp.utils.show
import com.mydrinksapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.LandingAnimator

@AndroidEntryPoint
class IngredientsFragment : Fragment(), AllIngredientsAdapter.OnIngredientsClickListener {

    private lateinit var binding: FragmentIngredientsBinding

    private var ingredientsMutableList: MutableList<AllIngredient> = mutableListOf()

    private var adapterAllIngredients: AllIngredientsAdapter = AllIngredientsAdapter(ingredientsMutableList, this)

    private var adapterCocktailByIngredients: CocktailByIngredientsAdapter =
        CocktailByIngredientsAdapter()

    private var adapterIngredientSelected: IngredientSelectedAdapter = IngredientSelectedAdapter()

    private val viewModel: IngredientsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        setupAllIngredientList()
        setupCocktailsByLetter()
        setupIngredientsByName()
        showIngredientSelected()

        return binding.root
    }

    private fun setupAllIngredientList() {
        viewModel.fetchAllIngredientList().observe(viewLifecycleOwner){
            when (it) {
                is Resource.Loading -> {
                    binding.progressBarCocktailsByIngredients.show()
                    binding.rvAllIngredients.hide()
                }
                is Resource.Success -> {
                    binding.progressBarCocktailsByIngredients.hide()
                    if (it.data.drinks.isEmpty()) {
                        binding.rvAllIngredients.hide()
                        return@observe
                    }
                    setupIngredientsRecyclerView()
                    adapterAllIngredients.setAllIngredientList(it.data.drinks.toMutableList())
                }
                is Resource.Failure -> {
                    binding.progressBarCocktailsByIngredients.hide()
                    showToast(getString(R.string.error_detail))
                }
            }
        }
    }

    private fun setupIngredientsRecyclerView() {
        binding.rvAllIngredients.apply {
            adapter = adapterAllIngredients
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            itemAnimator = LandingAnimator().apply { addDuration = 300 }
            setHasFixedSize(true)
            show()
        }
    }

    override fun onIngredientsClick(ingredients: String) {
        viewModel.setCocktailByIngredients(ingredients)
        setupCocktailsByLetter()
        viewModel.setIngredientsByName(ingredients)
        setupIngredientsByName()
    }

    private fun setupCocktailsByLetter() {
        viewModel.fetchCocktailByIngredients.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBarCocktailsByIngredients.show()
                    binding.rvCocktailsByIngredients.hide()
                }
                is Resource.Success -> {
                    binding.progressBarCocktailsByIngredients.hide()
                    if (it.data.drinks.isEmpty()) {
                        binding.rvCocktailsByIngredients.hide()
                        return@observe
                    }
                    setupCocktailsByIngredientsRecyclerView()
                    adapterCocktailByIngredients.setCocktailByIngredientsList(it.data.drinks)
                }
                is Resource.Failure -> {
                    binding.progressBarCocktailsByIngredients.hide()
                    showToast(getString(R.string.error_detail))
                }
            }
        }
    }

    private fun setupIngredientsByName() {
        viewModel.fetchIngredientsByName.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBarIngredientSelected.show()
                    binding.rvIngredientSelected.hide()
                }
                is Resource.Success -> {
                    binding.progressBarIngredientSelected.hide()
                    if (it.data.ingredients.isEmpty()) {
                        binding.rvIngredientSelected
                        return@observe
                    }
                    showIngredientSelected()
                    adapterIngredientSelected.setIngredientsByNameList(it.data.ingredients)
                }
                is Resource.Failure -> {
                    binding.progressBarIngredientSelected.hide()
                    showToast(getString(R.string.error_detail))
                }
            }
        }
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