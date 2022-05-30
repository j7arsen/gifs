package com.example.testtask.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.databinding.ItemGifLoadStateBinding

class GifsLoadingStateAdapter(
    private val onRetryAction: (() -> Unit)? = null
) : LoadStateAdapter<GifsLoadingStateAdapter.LoadingStateItemViewHolder>() {

    override fun onBindViewHolder(holder: LoadingStateItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateItemViewHolder {
        return LoadingStateItemViewHolder(
            ItemGifLoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class LoadingStateItemViewHolder(private val binding: ItemGifLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) = with(binding) {
            pbLoadGifs.isVisible = loadState is LoadState.Loading
            val isErrorState = loadState is LoadState.Error
            tvErrorMessage.isVisible = isErrorState
            tvErrorMessage.text = (loadState as? LoadState.Error)?.error?.localizedMessage.orEmpty()
            ivReload.apply {
                isVisible = isErrorState
                setOnClickListener { onRetryAction?.invoke() }
            }
        }
    }
}
