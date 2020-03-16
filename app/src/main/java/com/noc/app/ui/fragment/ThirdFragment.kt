package com.noc.app.ui.fragment

import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.noc.app.data.bean.SofaTab
import com.noc.app.utilities.AppConfig
import com.noc.app.viewmodels.TagListViewModel

class ThirdFragment : SecondFragment() {

    /**
     * 当子 Fragment 添加到此 Fragment
     * 监听页面切换
     */
    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        val tagType =
            childFragment.arguments!!.getString(TagListFragment.KEY_TAG_TYPE)
        // 判断是不是关注Tab
        if (TextUtils.equals(tagType, "onlyFollow")) {
            ViewModelProviders.of(childFragment).get(TagListViewModel::class.java)
                .switchTabLiveData.observe(this,
                    Observer<String> { viewPager2.currentItem = 1 })
        }
    }

    /**
     * 创建对应Fragment的页面
     */
    override fun getTabFragment(position: Int): Fragment {
        val tab: SofaTab.Tabs = getTabConfig().tabs[position]
        return TagListFragment.newInstance(tab.tag)
    }

    override fun getTabConfig(): SofaTab {
        return AppConfig.getFindTabConfig()
    }

}
