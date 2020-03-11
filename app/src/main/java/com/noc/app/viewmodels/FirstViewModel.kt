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
//        RequestCenter.hotFeedsList(mFeedType, 0, key, count, object : DisposeDataListener {
//            override fun onSuccess(responseObj: Any?) {
//                Log.i("TAG", "onSuccess$responseObj")
//                val result = JSONObject(responseObj.toString())
//                var data: List<Feed> =
//                    Gson().fromJson<Array<Feed>>(
//                        result.getJSONArray("data").toString()
//                    ).toMutableList()
//
//                callback.onResult(data)
//                Log.i("TAG", "onSuccess$responseObj")
//            }
//
//            override fun onFailure(reasonObj: Any?) {
//                Log.i("TAG", "onFailure$reasonObj")
//            }
//
//        })

        var resultStr = "{\"data\":[{\"id\":484,\"itemId\":1581239433864,\"itemType\":2,\"createTime\":1581239433891,\"duration\":14.0,\"feeds_text\":\"是时候表演真正的技术了\",\"authorId\":1578919786,\"activityIcon\":null,\"activityText\":\"2020新年快乐\",\"width\":960,\"height\":528,\"url\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com/zhenjishu.mp4\",\"cover\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com/zhenjishu2.png\",\"author\":{\"id\":1250,\"userId\":1578919786,\"name\":\"、蓅哖╰伊人为谁笑\",\"avatar\":\"http://qzapp.qlogo.cn/qzapp/101794421/FE41683AD4ECF91B7736CA9DB8104A5C/100\",\"description\":\"这是一只神秘的jetpack\",\"likeCount\":10,\"topCommentCount\":0,\"followCount\":2,\"followerCount\":37,\"qqOpenId\":\"FE41683AD4ECF91B7736CA9DB8104A5C\",\"expires_time\":1586695789903,\"score\":0,\"historyCount\":478,\"commentCount\":37,\"favoriteCount\":1,\"feedCount\":0,\"hasFollow\":false},\"topComment\":null,\"ugc\":{\"likeCount\":1528,\"shareCount\":29,\"commentCount\":586,\"hasFavorite\":false,\"hasLiked\":false,\"hasdiss\":false,\"hasDissed\":false}},{\"id\":483,\"itemId\":1581239433863,\"itemType\":1,\"createTime\":1581239433890,\"duration\":0.0,\"feeds_text\":\"智利车厘子\",\"authorId\":1578919786,\"activityIcon\":null,\"activityText\":\"2020新年快乐\",\"width\":0,\"height\":0,\"url\":null,\"cover\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com/chelizi.png\",\"author\":{\"id\":1250,\"userId\":1578919786,\"name\":\"、蓅哖╰伊人为谁笑\",\"avatar\":\"http://qzapp.qlogo.cn/qzapp/101794421/FE41683AD4ECF91B7736CA9DB8104A5C/100\",\"description\":\"这是一只神秘的jetpack\",\"likeCount\":10,\"topCommentCount\":0,\"followCount\":2,\"followerCount\":37,\"qqOpenId\":\"FE41683AD4ECF91B7736CA9DB8104A5C\",\"expires_time\":1586695789903,\"score\":0,\"historyCount\":478,\"commentCount\":37,\"favoriteCount\":1,\"feedCount\":0,\"hasFollow\":false},\"topComment\":null,\"ugc\":{\"likeCount\":1603,\"shareCount\":8,\"commentCount\":582,\"hasFavorite\":false,\"hasLiked\":false,\"hasdiss\":false,\"hasDissed\":false}},{\"id\":482,\"itemId\":1581239433862,\"itemType\":2,\"createTime\":1581239433889,\"duration\":20.0,\"feeds_text\":\"一盏茶，小时光\",\"authorId\":1578919786,\"activityIcon\":null,\"activityText\":\"2020新年快乐\",\"width\":640,\"height\":360,\"url\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com/Coffee%20-%2027230.mp4\",\"cover\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com/Coffee%20-%2027230.png\",\"author\":{\"id\":1250,\"userId\":1578919786,\"name\":\"、蓅哖╰伊人为谁笑\",\"avatar\":\"http://qzapp.qlogo.cn/qzapp/101794421/FE41683AD4ECF91B7736CA9DB8104A5C/100\",\"description\":\"这是一只神秘的jetpack\",\"likeCount\":10,\"topCommentCount\":0,\"followCount\":2,\"followerCount\":37,\"qqOpenId\":\"FE41683AD4ECF91B7736CA9DB8104A5C\",\"expires_time\":1586695789903,\"score\":0,\"historyCount\":478,\"commentCount\":37,\"favoriteCount\":1,\"feedCount\":0,\"hasFollow\":false},\"topComment\":null,\"ugc\":{\"likeCount\":1620,\"shareCount\":11,\"commentCount\":569,\"hasFavorite\":false,\"hasLiked\":false,\"hasdiss\":false,\"hasDissed\":false}},{\"id\":481,\"itemId\":1581239433861,\"itemType\":2,\"createTime\":1581239433888,\"duration\":14.0,\"feeds_text\":\"城市下的灯光，这是繁华应有的模样。\",\"authorId\":1578919786,\"activityIcon\":null,\"activityText\":\"2020新年快乐\",\"width\":2560,\"height\":1440,\"url\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com/Night%20-%2028860.mp4\",\"cover\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com/2222.png\",\"author\":{\"id\":1250,\"userId\":1578919786,\"name\":\"、蓅哖╰伊人为谁笑\",\"avatar\":\"http://qzapp.qlogo.cn/qzapp/101794421/FE41683AD4ECF91B7736CA9DB8104A5C/100\",\"description\":\"这是一只神秘的jetpack\",\"likeCount\":10,\"topCommentCount\":0,\"followCount\":2,\"followerCount\":37,\"qqOpenId\":\"FE41683AD4ECF91B7736CA9DB8104A5C\",\"expires_time\":1586695789903,\"score\":0,\"historyCount\":478,\"commentCount\":37,\"favoriteCount\":1,\"feedCount\":0,\"hasFollow\":false},\"topComment\":null,\"ugc\":{\"likeCount\":1614,\"shareCount\":15,\"commentCount\":558,\"hasFavorite\":false,\"hasLiked\":false,\"hasdiss\":false,\"hasDissed\":false}},{\"id\":480,\"itemId\":1581239433860,\"itemType\":1,\"createTime\":1581239433860,\"duration\":0.0,\"feeds_text\":\"克尔维特公路上的跑车,暖阳下我应芬芳，是谁家的姑娘，走在这条小桥上，我抚琴做忧伤\",\"authorId\":1578919786,\"activityIcon\":null,\"activityText\":\"2020新年快乐\",\"width\":0,\"height\":0,\"url\":null,\"cover\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com/corvette-4815234_640.jpg\",\"author\":{\"id\":1250,\"userId\":1578919786,\"name\":\"、蓅哖╰伊人为谁笑\",\"avatar\":\"http://qzapp.qlogo.cn/qzapp/101794421/FE41683AD4ECF91B7736CA9DB8104A5C/100\",\"description\":\"这是一只神秘的jetpack\",\"likeCount\":10,\"topCommentCount\":0,\"followCount\":2,\"followerCount\":37,\"qqOpenId\":\"FE41683AD4ECF91B7736CA9DB8104A5C\",\"expires_time\":1586695789903,\"score\":0,\"historyCount\":478,\"commentCount\":37,\"favoriteCount\":1,\"feedCount\":0,\"hasFollow\":false},\"topComment\":null,\"ugc\":{\"likeCount\":1601,\"shareCount\":12,\"commentCount\":558,\"hasFavorite\":false,\"hasLiked\":false,\"hasdiss\":false,\"hasDissed\":false}},{\"id\":428,\"itemId\":1578976510452,\"itemType\":2,\"createTime\":1578977844500,\"duration\":8.0,\"feeds_text\":\"2020他来了，就在眼前了\",\"authorId\":1578919786,\"activityIcon\":null,\"activityText\":\"2020新年快乐\",\"width\":960,\"height\":540,\"url\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com/New%20Year%20-%2029212-video.mp4\",\"cover\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com/2020%E5%B0%81%E9%9D%A2%E5%9B%BE.png\",\"author\":{\"id\":1250,\"userId\":1578919786,\"name\":\"、蓅哖╰伊人为谁笑\",\"avatar\":\"http://qzapp.qlogo.cn/qzapp/101794421/FE41683AD4ECF91B7736CA9DB8104A5C/100\",\"description\":\"这是一只神秘的jetpack\",\"likeCount\":10,\"topCommentCount\":0,\"followCount\":2,\"followerCount\":37,\"qqOpenId\":\"FE41683AD4ECF91B7736CA9DB8104A5C\",\"expires_time\":1586695789903,\"score\":0,\"historyCount\":478,\"commentCount\":37,\"favoriteCount\":1,\"feedCount\":0,\"hasFollow\":false},\"topComment\":{\"id\":1126,\"itemId\":1578976510452,\"commentId\":1579007787804000,\"userId\":1578919786,\"commentType\":1,\"createTime\":1579007787804,\"commentCount\":0,\"likeCount\":1001,\"commentText\":\"2020他来了，就在眼前了~Happy New Year\",\"imageUrl\":\"\",\"videoUrl\":\"\",\"width\":0,\"height\":0,\"hasLiked\":false,\"author\":{\"id\":1250,\"userId\":1578919786,\"name\":\"、蓅哖╰伊人为谁笑\",\"avatar\":\"http://qzapp.qlogo.cn/qzapp/101794421/FE41683AD4ECF91B7736CA9DB8104A5C/100\",\"description\":\"这是一只神秘的jetpack\",\"likeCount\":10,\"topCommentCount\":0,\"followCount\":2,\"followerCount\":37,\"qqOpenId\":\"FE41683AD4ECF91B7736CA9DB8104A5C\",\"expires_time\":1586695789903,\"score\":0,\"historyCount\":478,\"commentCount\":37,\"favoriteCount\":1,\"feedCount\":0,\"hasFollow\":false},\"ugc\":{\"likeCount\":111,\"shareCount\":10,\"commentCount\":10,\"hasFavorite\":false,\"hasLiked\":false,\"hasdiss\":false,\"hasDissed\":false}},\"ugc\":{\"likeCount\":1489,\"shareCount\":23,\"commentCount\":527,\"hasFavorite\":false,\"hasLiked\":false,\"hasdiss\":false,\"hasDissed\":false}},{\"id\":426,\"itemId\":1579005335869,\"itemType\":1,\"createTime\":1579005335869,\"duration\":0.0,\"feeds_text\":\"大熏熏在唱歌\",\"authorId\":1578919786,\"activityIcon\":null,\"activityText\":\"2019高光时刻\",\"width\":0,\"height\":0,\"url\":null,\"cover\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com/music-3507317_640.jpg\",\"author\":{\"id\":1250,\"userId\":1578919786,\"name\":\"、蓅哖╰伊人为谁笑\",\"avatar\":\"http://qzapp.qlogo.cn/qzapp/101794421/FE41683AD4ECF91B7736CA9DB8104A5C/100\",\"description\":\"这是一只神秘的jetpack\",\"likeCount\":10,\"topCommentCount\":0,\"followCount\":2,\"followerCount\":37,\"qqOpenId\":\"FE41683AD4ECF91B7736CA9DB8104A5C\",\"expires_time\":1586695789903,\"score\":0,\"historyCount\":478,\"commentCount\":37,\"favoriteCount\":1,\"feedCount\":0,\"hasFollow\":false},\"topComment\":null,\"ugc\":{\"likeCount\":1800,\"shareCount\":8,\"commentCount\":557,\"hasFavorite\":false,\"hasLiked\":false,\"hasdiss\":false,\"hasDissed\":false}},{\"id\":417,\"itemId\":1578921430555,\"itemType\":1,\"createTime\":1578921430555,\"duration\":0.0,\"feeds_text\":\"夜空中最亮的熏熏，2019的尾巴\",\"authorId\":1578919786,\"activityIcon\":null,\"activityText\":\"2019高光时刻\",\"width\":720,\"height\":1280,\"url\":null,\"cover\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com/2222.jpeg\",\"author\":{\"id\":1250,\"userId\":1578919786,\"name\":\"、蓅哖╰伊人为谁笑\",\"avatar\":\"http://qzapp.qlogo.cn/qzapp/101794421/FE41683AD4ECF91B7736CA9DB8104A5C/100\",\"description\":\"这是一只神秘的jetpack\",\"likeCount\":10,\"topCommentCount\":0,\"followCount\":2,\"followerCount\":37,\"qqOpenId\":\"FE41683AD4ECF91B7736CA9DB8104A5C\",\"expires_time\":1586695789903,\"score\":0,\"historyCount\":478,\"commentCount\":37,\"favoriteCount\":1,\"feedCount\":0,\"hasFollow\":false},\"topComment\":null,\"ugc\":{\"likeCount\":1993,\"shareCount\":4,\"commentCount\":520,\"hasFavorite\":false,\"hasLiked\":false,\"hasdiss\":false,\"hasDissed\":false}},{\"id\":416,\"itemId\":1578921365712,\"itemType\":2,\"createTime\":1578921365712,\"duration\":0.0,\"feeds_text\":\"夜空中最亮的熏熏\",\"authorId\":1578919786,\"activityIcon\":null,\"activityText\":\"2020新年快乐\",\"width\":720,\"height\":1280,\"url\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com/1578921330897.mp4\",\"cover\":\"https://pipijoke.oss-cn-hangzhou.aliyuncs.com/1111.jpeg\",\"author\":{\"id\":1250,\"userId\":1578919786,\"name\":\"、蓅哖╰伊人为谁笑\",\"avatar\":\"http://qzapp.qlogo.cn/qzapp/101794421/FE41683AD4ECF91B7736CA9DB8104A5C/100\",\"description\":\"这是一只神秘的jetpack\",\"likeCount\":10,\"topCommentCount\":0,\"followCount\":2,\"followerCount\":37,\"qqOpenId\":\"FE41683AD4ECF91B7736CA9DB8104A5C\",\"expires_time\":1586695789903,\"score\":0,\"historyCount\":478,\"commentCount\":37,\"favoriteCount\":1,\"feedCount\":0,\"hasFollow\":false},\"topComment\":{\"id\":1092,\"itemId\":1578921365712,\"commentId\":1578922158910003,\"userId\":1578919786,\"commentType\":1,\"createTime\":1578922158913,\"commentCount\":0,\"likeCount\":1002,\"commentText\":\"2020年新年快乐鸭~\",\"imageUrl\":null,\"videoUrl\":null,\"width\":0,\"height\":0,\"hasLiked\":false,\"author\":{\"id\":1250,\"userId\":1578919786,\"name\":\"、蓅哖╰伊人为谁笑\",\"avatar\":\"http://qzapp.qlogo.cn/qzapp/101794421/FE41683AD4ECF91B7736CA9DB8104A5C/100\",\"description\":\"这是一只神秘的jetpack\",\"likeCount\":10,\"topCommentCount\":0,\"followCount\":2,\"followerCount\":37,\"qqOpenId\":\"FE41683AD4ECF91B7736CA9DB8104A5C\",\"expires_time\":1586695789903,\"score\":0,\"historyCount\":478,\"commentCount\":37,\"favoriteCount\":1,\"feedCount\":0,\"hasFollow\":false},\"ugc\":{\"likeCount\":113,\"shareCount\":10,\"commentCount\":10,\"hasFavorite\":false,\"hasLiked\":false,\"hasdiss\":false,\"hasDissed\":false}},\"ugc\":{\"likeCount\":1996,\"shareCount\":8,\"commentCount\":524,\"hasFavorite\":false,\"hasLiked\":false,\"hasdiss\":false,\"hasDissed\":false}},{\"id\":415,\"itemId\":1578920778984,\"itemType\":1,\"createTime\":1578920778984,\"duration\":0.0,\"feeds_text\":\"2020跨时代的新年快乐\",\"authorId\":1578919786,\"activityIcon\":null,\"activityText\":\"2020新年快乐\",\"width\":0,\"height\":0,\"url\":null,\"cover\":null,\"author\":{\"id\":1250,\"userId\":1578919786,\"name\":\"、蓅哖╰伊人为谁笑\",\"avatar\":\"http://qzapp.qlogo.cn/qzapp/101794421/FE41683AD4ECF91B7736CA9DB8104A5C/100\",\"description\":\"这是一只神秘的jetpack\",\"likeCount\":10,\"topCommentCount\":0,\"followCount\":2,\"followerCount\":37,\"qqOpenId\":\"FE41683AD4ECF91B7736CA9DB8104A5C\",\"expires_time\":1586695789903,\"score\":0,\"historyCount\":478,\"commentCount\":37,\"favoriteCount\":1,\"feedCount\":0,\"hasFollow\":false},\"topComment\":null,\"ugc\":{\"likeCount\":2002,\"shareCount\":2,\"commentCount\":524,\"hasFavorite\":false,\"hasLiked\":false,\"hasdiss\":false,\"hasDissed\":false}}]}"
        val result = JSONObject(resultStr)
        var data: List<Feed> =
            Gson().fromJson<Array<Feed>>(
                result.getJSONArray("data").toString()
            ).toMutableList()

        callback.onResult(data)
    }


}