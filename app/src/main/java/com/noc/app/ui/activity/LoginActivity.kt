package com.noc.app.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.noc.app.R
import com.noc.app.data.bean.User
import com.noc.app.databinding.ActivityLoginBinding
import com.noc.lib_common_ui.base.BaseActivity
import com.noc.lib_network.global.AppGlobals
import com.noc.lib_network.okhttp2.ApiResponse
import com.noc.lib_network.okhttp2.ApiService
import com.noc.lib_network.okhttp2.JsonCallback

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login);
        binding.actionClose.setOnClickListener {
            finish()
        }
        binding.actionLogin.setOnClickListener {
            // QQ登录忽略，直接上传当前用户信息
            val nickname = "繁华小飞飞"
            val avatar = "http://qzapp.qlogo.cn/qzapp/101794421/EA371D505C3D8EC0102EE72FFBDB3427/100"
            val openid = "EA371D505C3D8EC0102EE72FFBDB3427"
            val expires_time = "1591858615597"
            saveInfo(nickname, avatar, openid, expires_time)
        }
    }

    private fun saveInfo(nickname: String, avatar: String, openid: String, expires_time: String) {
        ApiService.get<User>("/user/insert")
            .addParam("name", nickname)
            .addParam("avatar", avatar)
            .addParam("qqOpenId", openid)
            .addParam("expires_time", expires_time)
            .execute(object : JsonCallback<User>() {
                override fun onSuccess(response: ApiResponse<User>) {
                    if (response.body != null) {
                        UserManager.get().save(response.body)
                        finish()
                    } else {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "登陆失败", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

                override fun onError(response: ApiResponse<User>) {
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "登陆失败,msg:" + response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

}