package com.example.testtask.presentation.app

import androidx.lifecycle.LiveData
import com.example.testtask.core.base.BaseViewModel
import com.example.testtask.core.utils.SingleLiveEvent

class AppViewModel : BaseViewModel() {

    private val _openRootScreen: SingleLiveEvent<Unit> = SingleLiveEvent()
    val openRootScreen: LiveData<Unit> = _openRootScreen

    init {
        _openRootScreen.call()
    }
}
