package com.noc.app.viewmodels

import com.noc.app.viewmodels.factory.TestPagingViewModelFactory

/**
 * ViewModel提供者
 */
object CustomViewModelProvider {

    fun providerTestPagingViewModelFactory(): TestPagingViewModelFactory {
        return TestPagingViewModelFactory()
    }

}