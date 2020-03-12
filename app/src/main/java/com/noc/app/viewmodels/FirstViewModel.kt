package com.noc.app.viewmodels

import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.google.gson.reflect.TypeToken
import com.noc.app.data.bean.Feed
import com.noc.app.ui.AbsViewModel
import com.noc.lib_network.okhttp2.ApiResponse
import com.noc.lib_network.okhttp2.ApiService

class FirstViewModel : AbsViewModel<Feed>() {

    override fun createDataSource(): DataSource<*, *> {
        return FeedDataSource()
    }

    private val mFeedType: String? = null

    internal inner class FeedDataSource : ItemKeyedDataSource<Int, Feed>() {
        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Feed>
        ) {
            //加载初始化数据的
            loadData(0, params.requestedLoadSize, callback)
        }

        override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Feed>
        ) {
            //向后加载分页数据的
            loadData(params.key, params.requestedLoadSize, callback)
        }


        override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Feed>
        ) {
            //能够向前加载数据的
            callback.onResult(emptyList())
        }

        override fun getKey(item: Feed): Int {
            return item.id
        }


    }

    private fun loadData(
        key: Int,
        count: Int,
        callback: ItemKeyedDataSource.LoadCallback<Feed>
    ) {
        val request = ApiService.get<ApiResponse<List<Feed>>>("/feeds/queryHotFeedsList")
            .addParam("feedType", mFeedType)
            .addParam("userId", 0)
            .addParam("feedId", key)
            .addParam("pageCount", count)
            .responseType(object : TypeToken<List<Feed>>() {}.type)
        val response: ApiResponse<List<Feed>> = request.execute() as ApiResponse<List<Feed>>
        val data =
            if (response.body == null) emptyList<Feed>() else response.body
        callback.onResult(data)
    }


}