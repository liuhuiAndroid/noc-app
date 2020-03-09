package com.noc.app

import android.app.Application
import com.noc.lib_share.share.ShareManager

class NocApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // 分享组件初始化
        ShareManager.initSDK(this)
    }

}