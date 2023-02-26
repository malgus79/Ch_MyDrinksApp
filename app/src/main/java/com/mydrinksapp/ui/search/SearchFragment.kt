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
import androidx.recyclerview.widget.LinearLayoutManager
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

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchAdapter.OnTragoClickListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private val viewModel by activityViewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
        searchAdapter = SearchAdapter(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        setupRecyclerView()
        setupSearView()
        setupObserver()

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
                    binding.rvSearch.show()
                    searchAdapter.setCocktailList(result.data)
                    binding.emptyContainer.root.hide()
                }
                is Resource.Failure -> {
                    showToast("Ocurrió un error al traer los datos ${result.exception}")
                }
            }
        })
    }

    private fun setupSearView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setCocktail(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.main_menu, menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.favoritos -> {
//                findNavController().navigate(R.id.action_searchFragment_to_favoritesFragment)
//                false
//            }
//            else -> false
//        }
//    }

    override fun onCocktailClick(cocktail: Cocktail, position: Int) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                cocktail
            )
        )
//        val bundle = Bundle()
//        bundle.putParcelable("drink", cocktail)
//        findNavController().navigate(R.id.action_mainFragment_to_tragosDetalleFragment, bundle)
    }

    private fun setupRecyclerView() {
        binding.rvSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearch.adapter = searchAdapter
    }
}