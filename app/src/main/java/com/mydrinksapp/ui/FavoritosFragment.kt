package com.mydrinksapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.mydrinksapp.AppDatabase
import com.mydrinksapp.R
import com.mydrinksapp.data.DataSource
import com.mydrinksapp.databinding.FragmentFavoritosBinding
import com.mydrinksapp.domain.RepoImpl
import com.mydrinksapp.ui.viewmodel.MainViewModel
import com.mydrinksapp.ui.viewmodel.VMFactory
import com.mydrinksapp.vo.Resource

class FavoritosFragment : Fragment() {

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

        viewModel.getTragosFavoritos().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Log.d("LISTA DE FAVORITOS: ", "${result.data}")
                }
                is Resource.Failure -> {}
            }
        })
    }
}