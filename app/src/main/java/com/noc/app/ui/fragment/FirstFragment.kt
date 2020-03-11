package com.noc.app.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.paging.PagedListAdapter
import com.noc.app.adapters.FeedAdapter
import com.noc.app.api.RequestCenter
import com.noc.app.data.bean.Feed
import com.noc.app.ui.AbsListFragment
import com.noc.app.viewmodels.FirstViewModel
import com.noc.lib_network.okhttp.listener.DisposeDataListener
import com.scwang.smartrefresh.layout.api.RefreshLayout

class FirstFragment : AbsListFragment<Feed,FirstViewModel>() {

//    private lateinit var homeViewModel: FirstViewModel

    private var feedType: String? = null

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        homeViewModel =
//            ViewModelProviders.of(this).get(FirstViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_first, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//        textView.setOnClickListener {
//            // 跳转
//            ARouter.getInstance()
//                .build(Constant.Router.ROUTER_WEB_ACTIVIYT)
//                .withString("url", "https://www.baidu.com")
//                .navigation()
//
////            var intent: Intent = Intent(activity, AdBrowserActivity::class.java)
////            intent.putExtra("url", "https://www.baidu.com")
////            startActivity(intent)
//        }
//        return root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RequestCenter.login(object : DisposeDataListener {
            override fun onSuccess(responseObj: Any?) {

            }

            override fun onFailure(reasonObj: Any?) {

            }
        })
    }

    override fun getAdapter(): PagedListAdapter<*, *> {
        feedType = if (arguments == null) "all" else arguments!!.getString("feedType")
        return FeedAdapter(context, feedType)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {

    }

}
