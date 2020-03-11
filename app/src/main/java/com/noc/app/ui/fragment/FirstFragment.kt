package com.noc.app.ui.fragment

import androidx.paging.PagedListAdapter
import com.noc.app.adapters.FeedAdapter
import com.noc.app.data.bean.Feed
import com.noc.app.ui.AbsListFragment
import com.noc.app.viewmodels.FirstViewModel
import com.scwang.smartrefresh.layout.api.RefreshLayout

class FirstFragment : AbsListFragment<Feed,FirstViewModel>() {

    private var feedType: String? = null

    override fun getAdapter(): PagedListAdapter<*, *> {
        feedType = if (arguments == null) "all" else arguments!!.getString("feedType")
        return FeedAdapter(context, feedType)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {

    }

}
