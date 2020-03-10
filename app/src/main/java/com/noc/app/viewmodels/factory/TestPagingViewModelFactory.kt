package com.noc.app.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.noc.app.viewmodels.TestPagingViewModel

class TestPagingViewModelFactory() : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TestPagingViewModel() as T
    }

}