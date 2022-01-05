package com.example.vftask.features.imageDetails

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.io.File
import java.io.FileOutputStream


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

            with(binding) {
                txtAuthor.text = it.author
                txtUrl.text = it.openInLink
                txtUrl.setOnClickListener {
                    openUrl(txtUrl.text.toString())
                }
            }
        }

        viewModel.fetchImageDetails(args.image)
        observeState()
        show("my.png")
    }

    private fun openUrl(url: String) {
        startActivity(
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
        )
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
                                    //test(paletteState.bitmap)
                                }
                                paletteState.bodyTextColor?.let {
                                    txtAuthorLabel.setTextColor(it)
                                    txtAuthor.setTextColor(it)
                                    txtUrlLabel.setTextColor(it)
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


    private fun test(bitmap: Bitmap) {
        val fileName: String = "my.png"
        val file = File(requireContext().filesDir, fileName)
        if (!file.exists())
            file.createNewFile()
        var fileOutPutStream: FileOutputStream? = null
        try {
            fileOutPutStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutPutStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fileOutPutStream?.close()

            show(fileName)
        }
    }

    private fun show(fileName: String) {
        val cacheFile = File(context?.filesDir, fileName)
        Log.i(TAG, "show: ${cacheFile.absolutePath}")
        Log.i(TAG, "show: ${cacheFile.path}")

        val ffFile = File(cacheFile.absolutePath)

        Picasso.get().load(ffFile).placeholder(R.drawable.ic_loading)
            .into(binding.imageTest)
    }

}