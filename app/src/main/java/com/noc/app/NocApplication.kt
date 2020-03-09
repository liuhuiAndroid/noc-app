package com.noc.app

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.noc.lib_share.share.ShareManager
import com.noc.lib_update.app.UpdateHelper
import com.noc.lib_update.update.UpdateManager
import com.noc.lib_video.app.VideoHelper

class NocApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //更新组件下载
        UpdateHelper.init(this);
        // 分享组件初始化
        ShareManager.initSDK(this)
        // ARouter初始化
        ARouter.init(this)
    }

}