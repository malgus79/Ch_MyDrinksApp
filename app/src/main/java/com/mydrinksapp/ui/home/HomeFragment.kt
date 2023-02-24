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
    private val adapterByGlass: CocktailsByGlassAdapter = CocktailsByGlassAdapter()
    private val adapterCategories: CategoriesAdapter = CategoriesAdapter()

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        swipeRefresh()
        setupRandomCocktails()
        setupCategories()
        setupCocktailsByCategories()
        setupCocktailsByGlass()
        onBackPressedCallback()

        return binding.root
    }

    private fun swipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.setColorSchemeResources(R.color.purple_700)
            binding.swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(requireContext(), R.color.teal_200)
            )
            Handler(Looper.getMainLooper()).postDelayed({
                setupRandomCocktails()
                setupCategories()
                setupCocktailsByCategories()
                setupCocktailsByGlass()
            }, 500)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun setupRandomCocktails() {
        viewModel.fetchRandomCocktails().observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is Resource.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        progressBar.show()
                    }
                    is Resource.Success -> {
                        binding.progressBar.hide()
                        if (it.data.drinks.isEmpty()) {
                            binding.imgRandomCocktail.hide()
                            return@observe
                        }
                        setupShowRandomMeals(it.data.drinks.first())
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

    private fun setupShowRandomMeals(item: Cocktail) {
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

    private fun setupCategories() {
        viewModel.fetchAllCategories().observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBar.show()
                    }
                    is Resource.Success -> {
                        binding.progressBar.hide()
                        if (it.data.drinks.isEmpty()) {
                            binding.rvCocktailsByCategories.hide()
                            return@observe
                        }
                        setupCategoriesRecyclerView()
                        adapterCategories.setCategoriesList(it.data.drinks)
                        binding.txtTitleCategories.show()
                    }
                    is Resource.Failure -> {
                        binding.progressBar.hide()
                    }
                }
            }
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
    }

    private fun setupCocktailsByCategories() {
        val categoriesNames = arrayOf(
            "Ordinary Drink", "Cocktail", "Shake", "Other / Unknown", "Cocoa", "Shot",
            "Coffee / Tea", "Homemade Liqueur", "Punch / Party Drink", "Beer", "Soft Drink"
        )
        val randomCategory = categoriesNames[Random.nextInt(categoriesNames.size)]
        val titleCocktailsByCategories =
            "${getString(R.string.title_cocktails_by_categories)} $randomCategory "

        viewModel.fetchCocktailsByCategories(randomCategory).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }
                is Resource.Success -> {
                    binding.progressBar.hide()
                    if (it.data.drinks.isEmpty()) {
                        binding.rvCocktailsByCategories.hide()
                        return@observe
                    }
                    setupCocktailsByCategoriesRecyclerView()
                    adapterByCategories.setCocktailsByCategoriesList(it.data.drinks)
//                    binding.txtTitleCocktailsByCategories.text = titleCocktailsByCategories
                }
                is Resource.Failure -> {
                    binding.progressBar.hide()
                }
            }
        }
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
    }

    private fun setupCocktailsByGlass() {
        val glassNames = arrayOf(
            "Highball glass", "Cocktail glass", "Old-fashioned glass", "Whiskey Glass",
            "Collins glass", "Pousse cafe glass", "Champagne flute", "Whiskey sour glass",
            "Cordial glass", "Brandy snifter", "White wine glass", "Nick and Nora Glass",
            "Hurricane glass", "Coffee mug", "Shot glass", "Jar", "Irish coffee cup", "Punch bowl",
            "Pitcher", "Pint glass", "Copper Mug", "Wine Glass", "Beer mug",
            "Margarita/Coupette glass", "Beer pilsner", "Beer Glass", "Parfait glass", "Mason jar",
            "Margarita glass", "Martini Glass", "Balloon Glass", "Coupe Glass"
        )
        val randomCategory = glassNames[Random.nextInt(glassNames.size)]
        val titleCocktailsByGlass =
            "${getString(R.string.title_cocktails_by_glass)} $randomCategory "

        viewModel.fetchCocktailsByGlass(randomCategory).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }
                is Resource.Success -> {
                    binding.progressBar.hide()
                    if (it.data.drinks.isEmpty()) {
                        binding.rvCocktailsByGlass.hide()
                        return@observe
                    }
                    setupCocktailsByGlassRecyclerView()
                    adapterByGlass.setCocktailsByGlassList(it.data.drinks)
//                    binding.txtTitleCocktailsByGlass.text = titleCocktailsByGlass
                }
                is Resource.Failure -> {
                    binding.progressBar.hide()
                }
            }
        }
    }

    private fun setupCocktailsByGlassRecyclerView() {
        binding.rvCocktailsByGlass.apply {
            adapter = adapterByGlass
            layoutManager =
                GridLayoutManager(
                    requireContext(),
                    resources.getInteger(R.integer.columns_cocktails_by_glass),
                    GridLayoutManager.VERTICAL,
                    false
                )
            itemAnimator = LandingAnimator().apply { addDuration = 300 }
            setHasFixedSize(true)
            show()
        }
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
