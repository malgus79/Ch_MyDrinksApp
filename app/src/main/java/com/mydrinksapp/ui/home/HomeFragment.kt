package com.mydrinksapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
    private val adapterPopular: PopularAdapter = PopularAdapter()

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        swipeRefresh()
        setupRandomCocktails()
        setupPopularCocktails()
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
                setupPopularCocktails()
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
        binding.cardViewHomeRandom.show()
        binding.cardViewHomeRandom.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    item
                )
            )
        }
    }

    private fun setupPopularCocktails(){
        val categoriesNames = arrayOf(
            "Ordinary Drink", "Cocktail", "Shake", "Other / Unknown", "Cocoa", "Shot",
            "Coffee / Tea", "Homemade Liqueur", "Punch / Party Drink", "Beer", "Soft Drink"
        )
        val randomCategory = categoriesNames[Random.nextInt(categoriesNames.size)]
        val titlePopularMeals = "${getString(R.string.title_popular_cocktail)} $randomCategory "

        viewModel.fetchPopularCocktails(randomCategory).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    Log.d("STATUSSS","Loading")
                    binding.progressBar.show()
                }
                is Resource.Success -> {
                    Log.d("STATUSSS","OK")
                    binding.progressBar.hide()
                    if (it.data.drinks.isEmpty()) {
                        binding.rvPopularCocktail.hide()
                        return@observe
                    }
                    setupPopularCocktailsRecyclerView()
                    adapterPopular.setCocktailPopularList(it.data.drinks)
                    binding.txtTitlePopular.text = titlePopularMeals
                }
                is Resource.Failure -> {
                    Log.d("STATUSSS","Error ${it.exception}")
                    binding.progressBar.hide()
                }
            }
        }
    }

    private fun setupPopularCocktailsRecyclerView() {
        binding.rvPopularCocktail.apply {
            adapter = adapterPopular
            layoutManager =
                GridLayoutManager(
                    requireContext(),
                    resources.getInteger(R.integer.columns_popular),
                    GridLayoutManager.HORIZONTAL,
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
