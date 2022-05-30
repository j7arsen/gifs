package com.example.testtask.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.core.utils.extensions.loadGifFullScreen
import com.example.testtask.databinding.ItemGifDetailBinding
import com.example.testtask.domain.model.GifModel

class GifDetailAdapter :
    PagingDataAdapter<GifModel, GifDetailAdapter.GifDetailViewHolder>(DIFF_CALLBACK) {

    inner class GifDetailViewHolder(private val binding: ItemGifDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GifModel?) = with(binding) {
            item?.let { gif ->
                ivGif.loadGifFullScreen(gif = gif.url)
            }
        }
    }

    override fun onBindViewHolder(holder: GifDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifDetailViewHolder {
        val binding = ItemGifDetailBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return GifDetailViewHolder(binding)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GifModel>() {
            override fun areItemsTheSame(oldItem: GifModel, newItem: GifModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GifModel, newItem: GifModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
