package com.mydrinksapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mydrinksapp.R
import com.mydrinksapp.base.Resource
import com.mydrinksapp.databinding.FragmentHomeBinding
import com.mydrinksapp.model.data.Cocktail
import com.mydrinksapp.ui.viewmodel.HomeViewModel
import com.mydrinksapp.utils.hide
import com.mydrinksapp.utils.show
import com.mydrinksapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlin.random.Random

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapterByCategories: CocktailsByCategoriesAdapter = CocktailsByCategoriesAdapter()
    private val adapterCategories: CategoriesListAdapter = CategoriesListAdapter()

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        swipeRefresh()
        fetchAllCocktailsInHome()
        onBackPressedCallback()

        return binding.root
    }

    private fun swipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.setColorSchemeResources(R.color.purple_700)
            binding.swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(requireContext(), R.color.pink_light)
            )
            Handler(Looper.getMainLooper()).postDelayed({
                fetchAllCocktailsInHome()
            }, 500)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun fetchAllCocktailsInHome() {

        val categoriesNames = arrayOf(
            "Ordinary Drink", "Cocktail", "Shake", "Other / Unknown", "Cocoa", "Shot",
            "Coffee / Tea", "Homemade Liqueur", "Punch / Party Drink", "Beer", "Soft Drink"
        )
        val randomCategory = categoriesNames[Random.nextInt(categoriesNames.size)]

        viewModel.fetchAllCocktailsInHome(randomCategory).observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is Resource.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        progressBar.show()
                    }
                    is Resource.Success -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        binding.progressBar.hide()

                        setupShowRandomCocktails(it.data.first.drinks.first())

                        setupCocktailsByCategoriesRecyclerView()
                        adapterByCategories.setCocktailsByCategoriesList(it.data.second.drinks)

                        setupCategoriesRecyclerView()
                        adapterCategories.setCategoriesList(it.data.third.drinks)
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

    private fun setupShowRandomCocktails(item: Cocktail) {
        Glide.with(binding.root.context)
            .load(item.image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.gradient)
            .centerCrop()
            .into(binding.imgRandomCocktail)

        binding.txtTitleRandom.show()
        binding.mcvHomeRandom.show()
        binding.mcvHomeRandom.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    item
                )
            )
        }
    }

    private fun setupCategoriesRecyclerView() {
        binding.rvCategories.apply {
            adapter = adapterCategories
            layoutManager =
                GridLayoutManager(
                    requireContext(),
                    resources.getInteger(R.integer.columns_categories),
                    GridLayoutManager.VERTICAL,
                    false
                )
            itemAnimator = LandingAnimator().apply { addDuration = 300 }
            setHasFixedSize(true)
            show()
        }
        binding.txtTitleCategories.show()
    }

    private fun setupCocktailsByCategoriesRecyclerView() {
        binding.rvCocktailsByCategories.apply {
            adapter = adapterByCategories
            layoutManager =
                GridLayoutManager(
                    requireContext(),
                    resources.getInteger(R.integer.columns_cocktails_by_categories),
                    GridLayoutManager.VERTICAL,
                    false
                )
            itemAnimator = LandingAnimator().apply { addDuration = 300 }
            setHasFixedSize(true)
            show()
        }
        binding.txtTitleCocktailsByCategories.show()
    }

    private fun onBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )
    }
}
