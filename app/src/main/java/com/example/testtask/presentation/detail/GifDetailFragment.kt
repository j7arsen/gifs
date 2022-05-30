package com.example.testtask.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.example.testtask.R
import com.example.testtask.core.base.BaseActivity
import com.example.testtask.core.base.BaseFragment
import com.example.testtask.core.utils.IBackButtonListener
import com.example.testtask.databinding.FragmentGifDetailBinding
import com.example.testtask.presentation.detail.adapter.GifDetailAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.terrakok.cicerone.Router

class GifDetailFragment :
    BaseFragment<GifDetailViewModel, FragmentGifDetailBinding>(FragmentGifDetailBinding::inflate),
    IBackButtonListener {

    private val router: Router by inject()

    override val viewModel: GifDetailViewModel by viewModel {
        parametersOf(
            arguments?.getString(GIF_QUERY),
            arguments?.getString(ID_FROM)
        )
    }

    private val adapter by lazy { GifDetailAdapter() }

    override fun FragmentGifDetailBinding.initialize() {
        viewPager.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }

    override fun onInitViewModel(viewModel: GifDetailViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.gifs.collectLatest { movies ->
                adapter.submitData(movies)
            }
        }
    }

    private fun initToolbar() {
        val baseActivity = activity as BaseActivity
        val currentToolbar = binding.vToolbar.toolbar
        baseActivity.setSupportActionBar(currentToolbar)
        baseActivity.supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        currentToolbar.title = context?.getString(R.string.gif_detail_title)
        currentToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

    companion object {

        private const val GIF_QUERY = "GifDetailFragment.GIF_QUERY"
        private const val ID_FROM = "GifDetailFragment.ID_FROM"

        fun newInstance(query: String, idFrom: String?) = GifDetailFragment().apply {
            arguments = bundleOf(
                GIF_QUERY to query,
                ID_FROM to idFrom
            )
        }
    }
}
