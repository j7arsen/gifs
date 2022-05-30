package com.example.testtask.core.utils

import androidx.fragment.app.Fragment
import com.example.testtask.presentation.detail.GifDetailFragment
import com.example.testtask.presentation.list.GifsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object GifsScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = GifsFragment.newInstance()
    }

    data class GifDetailScreen(val query: String, val idFrom: String?) : SupportAppScreen() {
        override fun getFragment(): Fragment = GifDetailFragment.newInstance(query, idFrom)
    }
}
