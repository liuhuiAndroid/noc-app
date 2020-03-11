package com.noc.app.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.google.gson.Gson
import com.noc.app.api.RequestCenter
import com.noc.app.data.bean.Feed
import com.noc.app.ui.AbsViewModel
import com.noc.lib_network.okhttp.listener.DisposeDataListener
import org.json.JSONObject

import com.noc.app.core.ext.fromJson

class FirstViewModel : AbsViewModel<Feed>() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

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
            Log.e("FirstViewModel", "loadInitial: ")
            loadData(0, params.requestedLoadSize, callback)
        }

        override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Feed>
        ) {
            //向后加载分页数据的
            Log.e("FirstViewModel", "loadAfter: ")
            loadData(params.key, params.requestedLoadSize, callback)
        }


        override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Feed>
        ) {
            callback.onResult(emptyList())
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
        //  可以同步网络请求数据
        RequestCenter.hotFeedsList(mFeedType, 0, key, count, object : DisposeDataListener {
            override fun onSuccess(responseObj: Any?) {
                Log.i("TAG", "onSuccess$responseObj")
                /**
                 * 协议确定后看这里如何修改
                 */
                val result = JSONObject(responseObj.toString())
                var data: List<Feed> =
                    Gson().fromJson<Array<Feed>>(
                        result.getJSONArray("data").toString()
                    ).toMutableList()

                callback.onResult(data)
            }

            override fun onFailure(reasonObj: Any?) {
                Log.i("TAG", "onFailure$reasonObj")
            }

        })
    }


}