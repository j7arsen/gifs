package com.example.testtask.presentation.list

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.R
import com.example.testtask.core.base.BaseFragment
import com.example.testtask.core.utils.IBackButtonListener
import com.example.testtask.core.utils.Screens
import com.example.testtask.core.utils.extensions.hide
import com.example.testtask.core.utils.extensions.showWithText
import com.example.testtask.databinding.FragmentGifsBinding
import com.example.testtask.presentation.list.adapter.GifsAdapter
import com.example.testtask.presentation.list.adapter.GifsLoadingStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.Router

class GifsFragment :
    BaseFragment<GifsViewModel, FragmentGifsBinding>(FragmentGifsBinding::inflate),
    IBackButtonListener {

    override val viewModel: GifsViewModel by viewModel()

    private val router: Router by inject()

    private val adapter by lazy {
        GifsAdapter(
            onDeleteGifEvent = viewModel::onDeleteGifClicked,
            onItemCLickEvent = {
                showGifDetailScreen(
                    binding.etSearch.text.toString(),
                    it,
                )
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.deleteGifState.observe(this) { deleteState ->
            binding.pbLoadGifs.isVisible = deleteState is GifsViewModel.GifDeleteState.Loading
            when (deleteState) {
                is GifsViewModel.GifDeleteState.Success -> showToast(deleteState.message)
                is GifsViewModel.GifDeleteState.Error -> showToast(deleteState.message)
                GifsViewModel.GifDeleteState.Loading, GifsViewModel.GifDeleteState.Default -> return@observe
            }
        }
    }

    override fun FragmentGifsBinding.initialize() {
        rvGifs.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            itemAnimator = null
            adapter = this@GifsFragment.adapter.withLoadStateFooter(GifsLoadingStateAdapter(this@GifsFragment.adapter::retry))
        }
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadState ->
                pbLoadGifs.isVisible =
                    loadState.mediator?.refresh is LoadState.Loading || loadState.source.refresh is LoadState.Loading
                rvGifs.isVisible = loadState.mediator?.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.mediator?.refresh is LoadState.Error
                when (loadState.mediator?.refresh) {
                    is LoadState.NotLoading -> {
                        if (loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                            rvGifs.hide()
                            tvStatus.showWithText(requireContext().getString(R.string.gifs_empty_list))
                        } else {
                            tvStatus.hide()
                        }
                    }
                    is LoadState.Error -> tvStatus.showWithText((loadState.mediator?.refresh as? LoadState.Error)?.error?.message.orEmpty())
                    else -> tvStatus.hide()
                }
            }
        }
        btnRetry.setOnClickListener { adapter.retry() }
        etSearch.doAfterTextChanged { viewModel.searchGifs(it.toString()) }
    }

    override fun onInitViewModel(viewModel: GifsViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.gifs.collectLatest { movies ->
                adapter.submitData(movies)
            }
        }
    }

    private fun showGifDetailScreen(query: String, idFrom: String?) {
        router.navigateTo(Screens.GifDetailScreen(query, idFrom))
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

    companion object {
        fun newInstance() = GifsFragment()
    }
}
