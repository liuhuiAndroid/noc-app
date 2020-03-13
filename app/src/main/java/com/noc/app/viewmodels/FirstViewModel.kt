package com.noc.app.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.google.gson.reflect.TypeToken
import com.noc.app.data.bean.Feed
import com.noc.app.ui.AbsViewModel
import com.noc.app.ui.activity.UserManager
import com.noc.lib_network.okhttp2.ApiResponse
import com.noc.lib_network.okhttp2.ApiService
import java.util.concurrent.atomic.AtomicBoolean

class FirstViewModel : AbsViewModel<Feed>() {

    // 同步位标记，防止重复加载更多数据
    private val loadAfter = AtomicBoolean(false)

    override fun createDataSource(): DataSource<*, *> {
        return FeedDataSource()
    }

    private var mFeedType: String? = null

    fun setFeedType(feedType: String) {
        mFeedType = feedType
    }

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
        if (key > 0) {
            loadAfter.set(true)
        }
        // TODO 可以先加载缓存，暂时不实现
        // =============================
        val request = ApiService.get<ApiResponse<List<Feed>>>("/feeds/queryHotFeedsList")
            .addParam("feedType", mFeedType)
            .addParam("userId", UserManager.get().userId)
            .addParam("feedId", key)
            .addParam("pageCount", count)
            .responseType(object : TypeToken<List<Feed>>() {}.type)
        val response: ApiResponse<List<Feed>> = request.execute() as ApiResponse<List<Feed>>
        val data =
            if (response.body == null) emptyList<Feed>() else response.body
        callback.onResult(data)

        if (key > 0) {
            loadAfter.set(false)
        }
        Log.e("loadData", "loadData: key:$key")
    }

    @SuppressLint("RestrictedApi")
    fun loadAfter(id: Int, callback: ItemKeyedDataSource.LoadCallback<Feed>) {
        if (loadAfter.get()) {
            callback.onResult(emptyList<Feed>())
            return
        }
        ArchTaskExecutor.getIOThreadExecutor()
            .execute { loadData(id, config.pageSize, callback) }
    }
}