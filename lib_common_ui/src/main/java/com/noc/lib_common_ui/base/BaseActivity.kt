package com.noc.lib_common_ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.noc.lib_common_ui.utilities.StatusBarUtil

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 实现沉浸式效果
        // 实现沉浸式效果
        StatusBarUtil.statusBarLightMode(this)
    }

}