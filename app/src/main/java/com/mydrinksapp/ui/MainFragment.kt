package com.mydrinksapp.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mydrinksapp.R
import com.mydrinksapp.data.model.Cocktail
import com.mydrinksapp.databinding.FragmentMainBinding
import com.mydrinksapp.ui.viewmodel.MainViewModel
import com.mydrinksapp.utils.hide
import com.mydrinksapp.utils.show
import com.mydrinksapp.utils.showIf
import com.mydrinksapp.utils.showToast
import com.mydrinksapp.base.Resource
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

    }

    private fun setupObserver() {
        viewModel.fetchCocktailList.observe(viewLifecycleOwner, Observer{ result ->
            binding.progressBar.showIf { result is Resource.Loading }
            when (result) {
                is Resource.Loading -> {
                    binding.emptyContainer.root.hide()
//                    binding.progressBar.show()
                }
                is Resource.Success -> {
//                    binding.progressBar.hide()
                    if (result.data.isEmpty()) {
                        binding.rvTragos.hide()
                        binding.emptyContainer.root.show()
                        return@Observer
                    }
                    binding.rvTragos.show()
                    mainAdapter.setCocktailList(result.data)
                    binding.emptyContainer.root.hide()
                }
                is Resource.Failure -> {
//                    binding.progressBar.hide()
                    showToast("Ocurrió un error al traer los datos ${result.exception}")
    //                    Toast.makeText(
    //                        requireContext(),
    //                        "Ocurrio un error al trae los datos ${result.exception}",
    //                        Toast.LENGTH_SHORT
    //                    ).show()
                }
//                else -> {}
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

    override fun onCocktailClick(cocktail: Cocktail, position: Int) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToTragosDetalleFragment(cocktail))
//        val bundle = Bundle()
//        bundle.putParcelable("drink", cocktail)
//        findNavController().navigate(R.id.action_mainFragment_to_tragosDetalleFragment, bundle)
    }

    private fun setupRecyclerView() {
        binding.rvTragos.layoutManager = LinearLayoutManager(requireContext())
//        binding.rvTragos.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.HORIZONTAL))
        binding.rvTragos.adapter = mainAdapter
    }
}