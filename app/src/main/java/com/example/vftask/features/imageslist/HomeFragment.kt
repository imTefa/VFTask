package com.example.vftask.features.imageslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.vftask.R
import com.example.vftask.databinding.FragmentHomeBinding
import com.example.vftask.features.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeVewModel>()

    private lateinit var adapter: ImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSupportActionBar(getString(R.string.title_home_page))


        adapter = ImagesAdapter(emptyList()){

        }
        binding.list.adapter = adapter

            viewModel.fetchImages()

        observeData()
    }

    private fun observeData() {
        lifecycleScope.launchWhenResumed {
            viewModel.uiState
                .map { it.isLoading }
                .distinctUntilChanged()
                .collect { binding.progress.isVisible = it }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.uiState
                .collect { state ->
                    when {
                        state.isError -> showErrorMessage(state.errorMessage?:"")
                        state.isLoading -> {}
                        else -> {
                            adapter.update(state.images)
                            binding.emptyView.isVisible = state.images.isEmpty()
                        }
                    }
                }
        }


    }

    private fun showErrorMessage(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage ?: "", Snackbar.LENGTH_LONG).show()
    }
}