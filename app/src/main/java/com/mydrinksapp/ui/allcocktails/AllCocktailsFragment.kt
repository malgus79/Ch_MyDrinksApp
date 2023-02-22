package com.mydrinksapp.ui.allcocktails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mydrinksapp.databinding.FragmentAllCocktailsBinding
import com.mydrinksapp.utils.show
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.LandingAnimator

@AndroidEntryPoint
class AllCocktailsFragment : Fragment() {

    private lateinit var binding: FragmentAllCocktailsBinding
    private var letterMutableList: MutableList<Letter> = LetterProvider.letterList.toMutableList()
    private var adapterLetter: LetterAdapter = LetterAdapter(letterMutableList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCocktailsBinding.inflate(inflater, container, false)

        setupLetterRecyclerView()

        return binding.root
    }

    private fun setupLetterRecyclerView() {
        binding.rvLetter.apply {
            binding.rvLetter.adapter = adapterLetter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = LandingAnimator().apply { addDuration = 300 }
            setHasFixedSize(true)
            show()
        }
    }
}