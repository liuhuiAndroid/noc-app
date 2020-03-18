package com.noc.app.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.noc.app.R
import com.noc.app.data.bean.User
import com.noc.app.databinding.FragmentMyBinding
import com.noc.app.ui.activity.ProfileActivity
import com.noc.app.ui.activity.UserBehaviorListActivity
import com.noc.app.ui.activity.UserManager
import com.noc.lib_common_ui.utilities.StatusBarUtil2

class FourthFragment : Fragment() {

    private lateinit var mBinding: FragmentMyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMyBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user: User = UserManager.get().user
        mBinding.user = user
        UserManager.get().refresh().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                mBinding.user = it
            }
        })
        mBinding.actionLogout.setOnClickListener {
            AlertDialog.Builder(context!!)
                .setMessage(getString(R.string.fragment_my_logout))
                .setPositiveButton(
                    getString(R.string.fragment_my_logout_ok)
                ) { dialog: DialogInterface, which: Int ->
                    dialog.dismiss()
                    UserManager.get().logout()
                    activity!!.onBackPressed()
                }.setNegativeButton(getString(R.string.fragment_my_logout_cancel), null)
                .create().show()
        }
        mBinding.goDetail.setOnClickListener {
            ProfileActivity.startProfileActivity(
                context,
                ProfileActivity.TAB_TYPE_ALL
            )
        }
        mBinding.userFeed.setOnClickListener {
            ProfileActivity.startProfileActivity(
                context,
                ProfileActivity.TAB_TYPE_FEED
            )
        }
        mBinding.userComment.setOnClickListener {
            ProfileActivity.startProfileActivity(
                context,
                ProfileActivity.TAB_TYPE_COMMENT
            )
        }
        mBinding.userFavorite.setOnClickListener {
            UserBehaviorListActivity.startBehaviorListActivity(
                context,
                UserBehaviorListActivity.BEHAVIOR_FAVORITE
            )
        }
        mBinding.userHistory.setOnClickListener {
            UserBehaviorListActivity.startBehaviorListActivity(
                context,
                UserBehaviorListActivity.BEHAVIOR_HISTORY
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        StatusBarUtil2.lightStatusBar(activity, false)
        super.onCreate(savedInstanceState)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        StatusBarUtil2.lightStatusBar(activity, hidden)
    }

}
