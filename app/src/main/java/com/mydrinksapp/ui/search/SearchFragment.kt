package com.mydrinksapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mydrinksapp.R
import com.mydrinksapp.base.Resource
import com.mydrinksapp.databinding.FragmentSearchBinding
import com.mydrinksapp.model.data.Cocktail
import com.mydrinksapp.ui.viewmodel.SearchViewModel
import com.mydrinksapp.utils.hide
import com.mydrinksapp.utils.show
import com.mydrinksapp.utils.showIf
import com.mydrinksapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.LandingAnimator

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchAdapter.OnTragoClickListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private val viewModel by activityViewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        searchAdapter = SearchAdapter(requireContext(), this)

        binding.searchView.setQuery("", true)
        setupSearView()

        return binding.root
    }

    private fun setupSearView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setCocktail(query!!)
                setupObserver()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()) {
                    viewModel.setCocktail(newText)
                } else {
                    binding.rvSearch.hide()
                }
                setupObserver()
                return true
            }
        })
    }

    private fun setupObserver() {
        viewModel.fetchCocktailList.observe(viewLifecycleOwner, Observer { result ->
            binding.progressBar.showIf { result is Resource.Loading }
            when (result) {
                is Resource.Loading -> {
                    binding.emptyContainer.root.hide()
                }
                is Resource.Success -> {
                    if (result.data.isEmpty()) {
                        binding.rvSearch.hide()
                        binding.emptyContainer.root.show()
                        return@Observer
                    }
                    setupSearchRecyclerView()
                    searchAdapter.setCocktailList(result.data)
                    binding.emptyContainer.root.hide()
                }
                is Resource.Failure -> {
                    showToast(getString(R.string.error_detail))
                }
            }
        })
    }

    private fun setupSearchRecyclerView() {
        binding.rvSearch.apply {
            adapter = searchAdapter
            layoutManager = StaggeredGridLayoutManager(
                resources.getInteger(R.integer.columns_search),
                StaggeredGridLayoutManager.VERTICAL
            )
            itemAnimator = LandingAnimator().apply { addDuration = 300 }
            setHasFixedSize(true)
            show()
        }
    }

    override fun onCocktailClick(cocktail: Cocktail, position: Int) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                cocktail
            )
        )
    }
}