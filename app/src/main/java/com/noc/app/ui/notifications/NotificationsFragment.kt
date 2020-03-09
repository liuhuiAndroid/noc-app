package com.noc.app.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.noc.app.R
import com.noc.lib_video.videoplayer.VideoContextInterface
import com.noc.lib_video.videoplayer.core.VideoAdContext

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    private lateinit var mLlContainer: LinearLayout

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        mLlContainer = root.findViewById(R.id.mLlContainer)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val videoAdContext = VideoAdContext(
            mLlContainer,
            "https://www.apple.com/105/media/cn/iphone-x/2017/01df5b43-28e4-4848-bf20-490c34a926a7/films/feature/iphone-x-feature-cn-20170912_1280x720h.mp4"
        )
        videoAdContext.setAdResultListener(object : VideoContextInterface{
            override fun onVideoSuccess() {
                Toast.makeText(activity, "onVideoSuccess", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onVideoComplete() {
                Toast.makeText(activity, "onVideoComplete", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onVideoFailed() {
                Toast.makeText(activity, "onVideoFailed", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }

}
