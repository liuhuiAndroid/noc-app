package com.noc.app.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.noc.app.R
import com.noc.lib_common_ui.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 解决冷启动白屏,修改回默认Theme
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        navView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.navigation_third && !UserManager.get().isLogin) {
                UserManager.get().login(this)
                    .observe(this,
                        Observer<Any?> { navView.selectedItemId = item.itemId })
                false
            } else {
                navController.navigate(item.itemId)
                true
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // 如果当前正在显示的页面不是首页，而我们点击了返回键，则拦截。
        // TODO
        // 否则 finish，此处不宜调用onBackPressed。因为navigation会操作回退栈,切换到之前显示的页面。
        finish()
    }
}
