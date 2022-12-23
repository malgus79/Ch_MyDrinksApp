package com.mydrinksapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mydrinksapp.AppDatabase
import com.mydrinksapp.R
import com.mydrinksapp.data.DataSourceImpl
import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.databinding.FragmentMainBinding
import com.mydrinksapp.domain.RepoImpl
import com.mydrinksapp.ui.viewmodel.MainViewModel
import com.mydrinksapp.ui.viewmodel.VMFactory
import com.mydrinksapp.vo.Resource

class MainFragment : Fragment(), MainAdapter.OnTragoClickListener {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by activityViewModels<MainViewModel> {
        VMFactory(
            RepoImpl(
                DataSourceImpl(
                    AppDatabase.getDatabase(requireActivity().applicationContext)
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        binding.btnIrFavoritos.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_favoritosFragment)
        }

    }

    private fun setupObserver() {
        viewModel.fectchTragosList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progessBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progessBar.visibility = View.GONE
                    binding.rvTragos.adapter = MainAdapter(requireContext(), result.data, this)
                }
                is Resource.Failure -> {
                    binding.progessBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error al trae los datos ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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

    private fun setupRecyclerView() {
        binding.rvTragos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTragos.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.HORIZONTAL
            )
        )
    }

    override fun onTragoClick(drink: Drink, position:Int) {
        val bundle = Bundle()
        bundle.putParcelable("drink", drink)
        findNavController().navigate(R.id.action_mainFragment_to_tragosDetalleFragment, bundle)
    }
}