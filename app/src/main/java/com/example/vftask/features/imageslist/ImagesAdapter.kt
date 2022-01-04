package com.example.vftask.features.imageslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.vftask.R
import com.example.vftask.databinding.ListItemImageBinding
import com.example.vftask.features.utils.loadImageFromDrawable
import com.example.vftask.features.utils.loadImageFromUrl

class ImagesAdapter(
    private var list: List<ImageUIState>,
    private val onImageClicked: (ImageUIState) -> Unit
) : RecyclerView.Adapter<ImagesAdapter.Holder>() {


    inner class Holder(private val binding: ListItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val imageUIState = list[adapterPosition]
            binding.txtAuthor.text = imageUIState.author
            binding.image.loadImageFromUrl(imageUIState.loadUrl)

            if ((adapterPosition + 1) % 5 == 0) {
                binding.adView.isVisible = true
                //act like you are loading ad
                binding.imageAd.loadImageFromDrawable(R.drawable.ad_placeholder)
            } else {
                binding.adView.isVisible = false
            }

            binding.root.setOnClickListener {
                onImageClicked(imageUIState)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ListItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(images: List<ImageUIState>) {
        list = images
        notifyItemRangeChanged(0, itemCount)//TODO should be more efficient
    }
}