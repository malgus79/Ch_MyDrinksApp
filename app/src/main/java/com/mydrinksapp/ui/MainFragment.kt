package com.mydrinksapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mydrinksapp.R
import com.mydrinksapp.data.DataSource
import com.mydrinksapp.databinding.FragmentMainBinding
import com.mydrinksapp.domain.RepoImpl
import com.mydrinksapp.ui.viewmodel.MainViewModel
import com.mydrinksapp.ui.viewmodel.VMFactory
import com.mydrinksapp.vo.Resource

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<MainViewModel> { VMFactory(RepoImpl(DataSource())) }

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
        viewModel.fectchTragosList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progessBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progessBar.visibility = View.GONE
                    binding.rvTragos.adapter = MainAdapter(requireContext(), result.data)
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

    private fun setupRecyclerView() {
        binding.rvTragos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTragos.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.HORIZONTAL
            )
        )
    }
}