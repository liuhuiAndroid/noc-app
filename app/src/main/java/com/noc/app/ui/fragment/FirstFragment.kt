package com.noc.app.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import com.noc.app.adapters.FeedAdapter
import com.noc.app.data.bean.Feed
import com.noc.app.ui.AbsListFragment
import com.noc.app.ui.MutablePageKeyedDataSource
import com.noc.app.viewmodels.FirstViewModel
import com.noc.lib_video.exoplayer.PageListPlayDetector
import com.noc.lib_video.exoplayer.PageListPlayManager
import com.scwang.smartrefresh.layout.api.RefreshLayout

class FirstFragment : AbsListFragment<Feed, FirstViewModel>() {

    private var playDetector: PageListPlayDetector? = null

    private var shouldPause = true

    private var feedType: String? = ""

    override fun getAdapter(): PagedListAdapter<*, *> {
        feedType = if (arguments == null) "all" else arguments!!.getString("feedType")

        return object : FeedAdapter(context, feedType) {
            // 新的Item添加到页面上
            override fun onViewAttachedToWindow2(holder: ViewHolder) {
                if (holder.isVideoItem) {
                    playDetector?.addTarget(holder.getListPlayerView())
                }
            }

            // Item滑出屏幕
            override fun onViewDetachedFromWindow2(holder: ViewHolder) {
                playDetector?.removeTarget(holder.getListPlayerView())
            }

            // 跳转详情页面
            override fun onStartFeedDetailActivity(feed: Feed) {
                val isVideo = feed.itemType === Feed.TYPE_VIDEO
                shouldPause = !isVideo
            }

            override fun onCurrentListChanged(
                previousList: PagedList<Feed?>?,
                currentList: PagedList<Feed?>?
            ) {
                //这个方法是在我们每提交一次 pagelist对象到adapter 就会触发一次
                //每调用一次 adpater.submitlist
                if (previousList != null && currentList != null) {
                    if (!currentList.containsAll(previousList)) {
                        mRecyclerView.scrollToPosition(0)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        playDetector = PageListPlayDetector(this, mRecyclerView)
        feedType?.let {
            mViewModel.setFeedType(feedType!!)
        }
    }

    /**
     * 上拉分页，paging帮忙处理，但是如果数据返回为空，不会再次分页，需要手动加载
     */
    override fun onLoadMore(refreshLayout: RefreshLayout) {
        val currentList = adapter.currentList
        if (currentList == null || currentList.size <= 0) {
            finishRefresh(false)
            return
        }
        // 得到最后一个item
        val feed = currentList[adapter.itemCount - 1]
        // 手动加载更多
        feed?.let {
            mViewModel.loadAfter(feed.id, object : ItemKeyedDataSource.LoadCallback<Feed>() {
                override fun onResult(data: List<Feed?>) {
                    val config = currentList.config
                    if (data != null && data.isNotEmpty()) {
                        //这里 咱们手动接管 分页数据加载的时候 使用MutableItemKeyedDataSource也是可以的。
                        //由于当且仅当 paging不再帮我们分页的时候，我们才会接管。所以 就不需要ViewModel中创建的DataSource继续工作了，所以使用
                        //MutablePageKeyedDataSource也是可以的
                        val dataSource = MutablePageKeyedDataSource<Feed>()

                        //这里要把列表上已经显示的先添加到dataSource.data中
                        //而后把本次分页回来的数据再添加到dataSource.data中
                        dataSource.data.addAll(currentList)
                        dataSource.data.addAll(data)
                        val pagedList: PagedList<Feed> = dataSource.buildNewPagedList(config)
                        submitList(pagedList)
                    }
                }
            })
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            playDetector?.onPause()
        } else {
            playDetector?.onResume()
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        //invalidate 之后Paging会重新创建一个DataSource 重新调用它的loadInitial方法加载初始化数据
        //详情见：LivePagedListBuilder#compute方法
        mViewModel.dataSource.invalidate()
    }

    override fun onPause() {
        //如果是跳转到详情页,咱们就不需要 暂停视频播放了
        //如果是前后台切换 或者去别的页面了 都是需要暂停视频播放的
        if (shouldPause) {
            playDetector?.onPause()
        }
        Log.e("homefragment", "onPause: feedtype:$feedType")
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        shouldPause = true
        // 由于沙发Tab的几个子页面 复用了HomeFragment。
        // 我们需要判断下 当前页面 它是否有ParentFragment.
        // 当且仅当 它和它的ParentFragment均可见的时候，才能恢复视频播放
        if (parentFragment != null) {
            if (parentFragment!!.isVisible && isVisible) {
                Log.e("homefragment", "onResume: feedtype:$feedType")
                playDetector!!.onResume()
            }
        } else {
            // isVisible 判断是否可见
            if (isVisible) {
                Log.e("homefragment", "onResume: feedtype:$feedType")
                playDetector!!.onResume()
            }
        }
    }

    override fun onDestroy() {
        //记得销毁
        PageListPlayManager.release(feedType)
        super.onDestroy()
    }

    companion object {

        fun newInstance(feedType: String?): FirstFragment {
            val args = Bundle()
            args.putString("feedType", feedType)
            val fragment: FirstFragment = FirstFragment()
            fragment.arguments = args
            return fragment
        }

    }

}
