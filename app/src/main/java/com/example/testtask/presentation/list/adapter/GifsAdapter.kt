package com.example.testtask.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.R
import com.example.testtask.core.utils.extensions.loadGif
import com.example.testtask.databinding.ItemGifBinding
import com.example.testtask.domain.model.GifModel

class GifsAdapter(
    private val onDeleteGifEvent: ((String?) -> Unit),
    private val onItemCLickEvent: ((String?) -> Unit)
) :
    PagingDataAdapter<GifModel, GifsAdapter.GifViewHolder>(DIFF_CALLBACK) {

    inner class GifViewHolder(private val binding: ItemGifBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemCLickEvent.invoke(getItem(absoluteAdapterPosition)?.id)
            }
        }

        fun bind(item: GifModel) = with(binding) {
            tvCreatedDate.text = item.createDate
            tvTitle.text = item.title
            ivMore.layout(0, 0, 0, 0)
            ivGif.loadGif(gif = item.url)
            ivMore.setOnClickListener {
                PopupMenu(itemView.context, ivMore).apply {
                    menuInflater.inflate(R.menu.menu_gif_more, menu)
                    setOnMenuItemClickListener {
                        onDeleteGifEvent.invoke(item.id)
                        return@setOnMenuItemClickListener true
                    }
                }.show()
            }
        }
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding = ItemGifBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(binding)
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
