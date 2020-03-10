package com.noc.app

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.noc.lib_share.share.ShareManager
import com.noc.lib_update.app.UpdateHelper

class NocApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // ARouter初始化
        ARouter.init(this)
        //更新组件下载
        UpdateHelper.init(this);
        // 分享组件初始化
        ShareManager.initSDK(this)
    }

}