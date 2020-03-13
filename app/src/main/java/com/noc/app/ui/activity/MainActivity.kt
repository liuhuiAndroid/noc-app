package com.noc.app.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
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
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_first,
                R.id.navigation_second,
                R.id.navigation_third
            )
        )
        // setupActionBarWithNavController(navController, appBarConfiguration)
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

}
