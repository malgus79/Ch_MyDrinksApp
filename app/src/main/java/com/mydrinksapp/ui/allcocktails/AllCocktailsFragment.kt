package com.mydrinksapp.ui.allcocktails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.mydrinksapp.R
import com.mydrinksapp.core.Resource
import com.mydrinksapp.core.utils.hide
import com.mydrinksapp.core.utils.show
import com.mydrinksapp.core.utils.showToast
import com.mydrinksapp.databinding.FragmentAllCocktailsBinding
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.ui.allcocktails.adapter.CocktailByLetterAdapter
import com.mydrinksapp.ui.allcocktails.letter.Letter
import com.mydrinksapp.ui.allcocktails.letter.LetterAdapter
import com.mydrinksapp.ui.allcocktails.letter.LetterProvider
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.LandingAnimator

@AndroidEntryPoint
class AllCocktailsFragment : Fragment(), LetterAdapter.OnLetterClickListener {

    private lateinit var binding: FragmentAllCocktailsBinding

    private var letterMutableList: MutableList<Letter> = LetterProvider.letterList.toMutableList()
    private var adapterLetter: LetterAdapter = LetterAdapter(letterMutableList, this)
    private var adapterCocktailByLetter: CocktailByLetterAdapter = CocktailByLetterAdapter()

    private lateinit var cocktail: Cocktail

    private val viewModel: AllCocktailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCocktailsBinding.inflate(inflater, container, false)
        cocktail = Cocktail()

        setupLettersCarousel()
        setupCocktailsByLetter()

        return binding.root
    }

    private fun setupLettersCarousel() {
        adapterLetter = LetterAdapter(letterMutableList, this)
        binding.rvLetter.apply {
            this.adapter = adapterLetter
            set3DItem(true)
            setAlpha(true)
        }
    }

    override fun onLetterClick(letter: String) {
        viewModel.setCocktailByLetter(letter)
        setupCocktailsByLetter()
    }

    private fun setupCocktailsByLetter() {
        viewModel.fetchCocktailByLetter.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                    binding.rvCocktailsByLetter.hide()
                }
                is Resource.Success -> {
                    binding.progressBar.hide()
                    if (it.data.drinks.isEmpty()) {
                        binding.rvCocktailsByLetter.hide()
                        return@observe
                    }
                    setupCocktailsByLetterRecyclerView()
                    adapterCocktailByLetter.setCocktailByLetterList(it.data.drinks)
                }
                is Resource.Failure -> {
                    binding.progressBar.hide()
                    showToast(getString(R.string.error_detail))
                }
            }
        }
    }

    private fun setupCocktailsByLetterRecyclerView() {
        binding.rvCocktailsByLetter.apply {
            binding.rvCocktailsByLetter.adapter = adapterCocktailByLetter
            layoutManager = GridLayoutManager(
                requireContext(),
                resources.getInteger(R.integer.columns_all_cocktails_by_letter),
                GridLayoutManager.VERTICAL,
                false
            )
            itemAnimator = LandingAnimator().apply { addDuration = 300 }
            setHasFixedSize(true)
            show()
        }
    }
}