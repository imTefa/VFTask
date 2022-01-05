package com.example.vftask.features.imageslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vftask.R
import com.example.vftask.databinding.FragmentHomeBinding
import com.example.vftask.features.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

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

        initRecyclerView()

        viewModel.fetchImages()

        observeData()
    }

    private fun initRecyclerView() {
        adapter = ImagesAdapter(emptyList()) {
            findNavController().navigate(
                HomeFragmentDirections.actionImagesListFragmentToImageDetailsFragment(
                    it
                )
            )
        }
        binding.list.adapter = adapter

        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadMore()
                }
            }

        })
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
                        state.isError -> showErrorMessage(binding.root, state.errorMessage ?: "")
                        state.isLoading -> {
                        }
                        else -> {
                            adapter.update(state.images)
                            binding.emptyView.isVisible = state.images.isEmpty()
                        }
                    }
                }
        }

    }

}