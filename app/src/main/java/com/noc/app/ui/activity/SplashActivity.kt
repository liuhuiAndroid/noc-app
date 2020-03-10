package com.noc.app.ui.activity

import android.content.Intent
import android.os.Bundle
import com.noc.lib_common_ui.base.BaseActivity
import com.noc.lib_pullalive.app.AliveJobService

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcherAgain()
        pullAliveService()
    }

    /**
     * 避免从桌面启动程序后，会重新实例化入口类的activity
     */
    private fun launcherAgain() {
        // 判断当前activity是不是所在任务栈的根
        if (!this.isTaskRoot) {
            val intent = intent
            if (intent != null) {
                val action = intent.action
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                    finish()
                }
            }
        }
    }

    /**
     * 启动保活组件
     */
    private fun pullAliveService() {
        AliveJobService.start(this)
    }

}