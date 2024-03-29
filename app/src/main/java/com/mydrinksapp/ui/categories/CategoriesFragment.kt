package com.mydrinksapp.ui.categories

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.mydrinksapp.R
import com.mydrinksapp.core.Resource
import com.mydrinksapp.core.utils.hide
import com.mydrinksapp.core.utils.show
import com.mydrinksapp.core.utils.showToast
import com.mydrinksapp.databinding.FragmentCategoriesBinding
import com.mydrinksapp.ui.categories.adapter.CategoriesAdapter
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.LandingAnimator

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private val adapterCategories: CategoriesAdapter = CategoriesAdapter()
    private val args by navArgs<CategoriesFragmentArgs>()
    private val viewModel: CategoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)

        swipeRefresh()
        setupCocktailsByCategories()

        return binding.root
    }

    private fun swipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.setColorSchemeResources(R.color.purple_700)
            binding.swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(requireContext(), R.color.pink_light)
            )
            Handler(Looper.getMainLooper()).postDelayed({
                setupCocktailsByCategories()
            }, 500)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun setupCocktailsByCategories() {
        viewModel.setCocktailsByCategories(args.nameOfCategory)
        showDataCocktailsByCategories()
    }

    private fun showDataCocktailsByCategories() {
        viewModel.fetchCocktailsByCategories.observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is Resource.Loading -> {
                        if (swipeRefreshLayout.isRefreshing) {
                            progressBar.hide()
                        } else {
                            progressBar.show()
                        }
                    }

                    is Resource.Success -> {
                        swipeRefreshLayout.isRefreshing = false
                        progressBar.hide()
                        if (it.data.drinks.isEmpty()) {
                            rvCocktailsByCategories.hide()
                            return@observe
                        }
                        setupCocktailsByCategoryRecyclerView()
                        adapterCategories.setCocktailsByCategories(it.data.drinks)
                        countCocktailsByCategories(it.data.drinks.size)
                        showTitleCocktailsByCategory()
                    }

                    is Resource.Failure -> {
                        swipeRefreshLayout.isRefreshing = false
                        progressBar.hide()
                        showToast(getString(R.string.error_detail))
                    }
                }
            }
        }
    }

    private fun setupCocktailsByCategoryRecyclerView() {
        binding.rvCocktailsByCategories.apply {
            adapter = adapterCategories
            layoutManager =
                GridLayoutManager(
                    requireContext(),
                    resources.getInteger(R.integer.columns_cocktails_by_categories_in_categories),
                    GridLayoutManager.VERTICAL,
                    false
                )
            itemAnimator = LandingAnimator().apply { addDuration = 300 }
            setHasFixedSize(true)
            show()
        }
    }

    private fun showTitleCocktailsByCategory() {
        val cocktailsByCategoryTitle =
            "${args.nameOfCategory} "
        binding.txtCocktailsByCategoryTitle.text = cocktailsByCategoryTitle
    }

    @SuppressLint("SetTextI18n")
    private fun countCocktailsByCategories(size: Int) {
        binding.txtCountOfCocktailsByCategory.text = "($size)"
    }
}