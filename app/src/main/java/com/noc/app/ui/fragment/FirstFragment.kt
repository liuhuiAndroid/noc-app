package com.noc.app.ui.fragment

import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import com.noc.app.adapters.FeedAdapter
import com.noc.app.data.bean.Feed
import com.noc.app.ui.AbsListFragment
import com.noc.app.ui.MutablePageKeyedDataSource
import com.noc.app.viewmodels.FirstViewModel
import com.scwang.smartrefresh.layout.api.RefreshLayout

class FirstFragment : AbsListFragment<Feed, FirstViewModel>() {

    private var feedType: String? = null

    override fun getAdapter(): PagedListAdapter<*, *> {
        feedType = if (arguments == null) "all" else arguments!!.getString("feedType")
        return FeedAdapter(context, feedType)
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

    override fun onRefresh(refreshLayout: RefreshLayout) {
        //invalidate 之后Paging会重新创建一个DataSource 重新调用它的loadInitial方法加载初始化数据
        //详情见：LivePagedListBuilder#compute方法
        mViewModel.dataSource.invalidate()
    }

}
