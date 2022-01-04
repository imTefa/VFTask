package com.example.vftask.features.imageDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.vftask.R
import com.example.vftask.databinding.FragmentImageDetailsBinding
import com.example.vftask.features.BaseFragment
import com.example.vftask.features.utils.loadImageFromUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


private const val TAG = "ImageDetailsFragment"

@AndroidEntryPoint
class ImageDetailsFragment : BaseFragment() {

    private val viewModel: ImageDetailsViewModel by viewModels()
    private val args: ImageDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentImageDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentImageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.image.let {
            setupSupportActionBar(it.author)

            binding.image.loadImageFromUrl(it.loadUrl)
            binding.txtAuthor.text = it.author
        }

        viewModel.fetchImageDetails(args.image)
        observeState()
    }

    private fun observeState() {
        lifecycleScope.launchWhenResumed {
            viewModel.uiState.collect { state ->
                when {
                    state.isLoading -> binding.image.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_loading
                        )
                    )
                    state.isError -> showErrorMessage(binding.root, state.errorMessage ?: "")
                    else -> {
                        state.paletteUiState?.let { paletteState ->
                            with(binding) {
                                image.setImageBitmap(paletteState.bitmap)
                                paletteState.backgroundColor?.let {
                                    container.setBackgroundColor(it)
                                }
                                paletteState.bodyTextColor?.let {
                                    txtAuthorLabel.setTextColor(it)
                                    txtAuthor.setTextColor(it)
                                    txtUrl.setTextColor(it)
                                }

                                paletteState.titleTextColor?.let {
                                    //TODO we may change toolbar and title color if there is enough time.
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}