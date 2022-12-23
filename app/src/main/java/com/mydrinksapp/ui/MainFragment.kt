package com.mydrinksapp.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mydrinksapp.R
import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.databinding.FragmentMainBinding
import com.mydrinksapp.ui.viewmodel.MainViewModel
import com.mydrinksapp.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), MainAdapter.OnTragoClickListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var mainAdapter:MainAdapter
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mainAdapter = MainAdapter(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        setupRecyclerView()
        setupSearView()
        setupObserver()

//        binding.btnIrFavoritos.setOnClickListener {
//            findNavController().navigate(R.id.action_mainFragment_to_favoritosFragment)
//        }

    }

    private fun setupObserver() {
        viewModel.fectchTragosList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.emptyContainer.root.visibility = View.GONE
                    binding.progessBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progessBar.visibility = View.GONE
                    if (result.data.isEmpty()) {
                        binding.emptyContainer.root.visibility = View.VISIBLE
                        return@Observer
                    }
                    mainAdapter.setCocktailList(result.data)
                    binding.emptyContainer.root.visibility = View.GONE
                }
                is Resource.Failure -> {
                    binding.progessBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error al trae los datos ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {}
            }
        })
    }

    private fun setupSearView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setTrago(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favoritos -> {
                findNavController().navigate(R.id.action_mainFragment_to_favoritosFragment)
                false
            }
            else -> false
        }
    }

    override fun onCocktailClick(drink: Drink, position: Int) {
        val bundle = Bundle()
        bundle.putParcelable("drink", drink)
        findNavController().navigate(R.id.action_mainFragment_to_tragosDetalleFragment, bundle)
    }

    private fun setupRecyclerView() {
        binding.rvTragos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTragos.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.HORIZONTAL))
        binding.rvTragos.adapter = mainAdapter
    }
}