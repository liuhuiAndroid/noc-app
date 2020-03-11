package com.noc.app.viewmodels

import android.util.Log
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedList
import com.alibaba.fastjson.TypeReference
import com.mooc.libnetwork.ApiResponse
import com.mooc.libnetwork.ApiService
import com.mooc.libnetwork.JsonCallback
import com.mooc.libnetwork.Request
import com.mooc.ppjoke.model.Feed
import com.mooc.ppjoke.ui.AbsViewModel
import com.mooc.ppjoke.ui.MutablePageKeyedDataSource
import com.mooc.ppjoke.ui.login.UserManager
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class HomeViewModel : AbsViewModel<Feed?>() {
    @Volatile
    private var witchCache = true
    private val cacheLiveData: MutableLiveData<PagedList<Feed>> =
        MutableLiveData<PagedList<Feed>>()
    private val loadAfter =
        AtomicBoolean(false)
    private var mFeedType: String? = null
    fun createDataSource(): DataSource<*, *> {
        return FeedDataSource()
    }

    fun getCacheLiveData(): MutableLiveData<PagedList<Feed>> {
        return cacheLiveData
    }

    fun setFeedType(feedType: String?) {
        mFeedType = feedType
    }

    internal inner class FeedDataSource : ItemKeyedDataSource<Int, Feed>() {
        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Feed>
        ) {
            //加载初始化数据的
            Log.e("homeviewmodel", "loadInitial: ")
            loadData(0, params.requestedLoadSize, callback)
            witchCache = false
        }

        override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Feed>
        ) {
            //向后加载分页数据的
            Log.e("homeviewmodel", "loadAfter: ")
            loadData(params.key, params.requestedLoadSize, callback)
        }

        override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Feed>
        ) {
            callback.onResult(emptyList<Feed>())
            //能够向前加载数据的
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
        //feeds/queryHotFeedsList
        val request: Request = ApiService.get("/feeds/queryHotFeedsList")
            .addParam("feedType", mFeedType)
            .addParam("userId", UserManager.get().getUserId())
            .addParam("feedId", key)
            .addParam("pageCount", count)
            .responseType(object : TypeReference<ArrayList<Feed?>?>() {}.getType())
        if (witchCache) {
            request.cacheStrategy(Request.CACHE_ONLY)
            request.execute(object : JsonCallback<List<Feed?>?>() {
                fun onCacheSuccess(response: ApiResponse<List<Feed?>?>) {
                    Log.e("loadData", "onCacheSuccess: ")
                    val dataSource = MutablePageKeyedDataSource<Feed>()
                    dataSource.data.addAll(response.body)
                    val pagedList: PagedList<*> = dataSource.buildNewPagedList(config)
                    cacheLiveData.postValue(pagedList)

                    //下面的不可取，否则会报
                    // java.lang.IllegalStateException: callback.onResult already called, cannot call again.
                    //if (response.body != null) {
                    //  callback.onResult(response.body);
                    // }
                }
            })
        }
        try {
            val netRequest: Request = if (witchCache) request.clone() else request
            netRequest.cacheStrategy(if (key == 0) Request.NET_CACHE else Request.NET_ONLY)
            val response: ApiResponse<List<Feed>> = netRequest.execute()
            val data: List<Feed> =
                if (response.body == null) emptyList<Feed>() else response.body
            callback.onResult(data)
            if (key > 0) {
                //通过BoundaryPageData发送数据 告诉UI层 是否应该主动关闭上拉加载分页的动画
                (getBoundaryPageData() as MutableLiveData<*>).postValue(data.size > 0)
                loadAfter.set(false)
            }
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        Log.e("loadData", "loadData: key:$key")
    }

    fun loadAfter(id: Int, callback: ItemKeyedDataSource.LoadCallback<Feed>) {
        if (loadAfter.get()) {
            callback.onResult(emptyList<Feed>())
            return
        }
        ArchTaskExecutor.getIOThreadExecutor()
            .execute { loadData(id, config.pageSize, callback) }
    }
}