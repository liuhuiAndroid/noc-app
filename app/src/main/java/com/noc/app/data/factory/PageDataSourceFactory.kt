package com.noc.app.data.factory

import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.noc.app.data.TestStudent

class PageDataSourceFactory(var mPositionalDataSource: PositionalDataSource<TestStudent>) :
    DataSource.Factory<Any?, Any?>() {

    override fun create(): DataSource<Any?, Any?> {
        return mPositionalDataSource as DataSource<Any?, Any?>
    }

}