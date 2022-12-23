package com.mydrinksapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mydrinksapp.AppDatabase
import com.mydrinksapp.R
import com.mydrinksapp.data.DataSource
import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.databinding.FragmentFavoritosBinding
import com.mydrinksapp.domain.RepoImpl
import com.mydrinksapp.ui.viewmodel.MainViewModel
import com.mydrinksapp.ui.viewmodel.VMFactory
import com.mydrinksapp.vo.Resource

class FavoritosFragment : Fragment(), MainAdapter.OnTragoClickListener {

    private lateinit var binding: FragmentFavoritosBinding
    private val viewModel by activityViewModels<MainViewModel> {
        VMFactory(
            RepoImpl(
                DataSource(
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
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritosBinding.bind(view)

        setupRecyclerView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.getTragosFavoritos().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val lista: List<Drink> = result.data.map {
                        Drink(it.tragoId, it.imagen, it.nombre, it.descripcion, it.hasAlcohol)
                    }
                    binding.rvTragosFavoritos.adapter = MainAdapter(requireContext(), lista, this)

                    //Log.d("LISTA DE FAVORITOS: ", "${result.data}")
                }
                is Resource.Failure -> {}
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvTragosFavoritos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTragosFavoritos.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.HORIZONTAL
            )
        )
    }

    override fun onTragoClick(drink: Drink) {

    }
}