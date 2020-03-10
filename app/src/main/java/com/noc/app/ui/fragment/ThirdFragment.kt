package com.noc.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.noc.app.R
import com.noc.app.viewmodels.ThirdViewModel
import com.noc.lib_video.videoplayer.core.VideoAdContext

class ThirdFragment : Fragment() {

    private lateinit var notificationsViewModel: ThirdViewModel

    private lateinit var mLlContainer: LinearLayout

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(ThirdViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_third, container, false)
        mLlContainer = root.findViewById(R.id.mLlContainer)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        VideoAdContext(
            mLlContainer,
            "https://www.apple.com/105/media/cn/iphone-x/2017/01df5b43-28e4-4848-bf20-490c34a926a7/films/feature/iphone-x-feature-cn-20170912_1280x720h.mp4"
        )
    }

}
