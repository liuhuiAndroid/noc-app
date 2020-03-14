package com.noc.app.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.noc.app.data.bean.Feed
import com.noc.lib_common_ui.base.BaseActivity

class FeedDetailActivity : BaseActivity() {

    private lateinit var viewHandler: ViewHandler

    companion object{
        const val KEY_FEED = "key_feed"
        const val KEY_CATEGORY = "key_category"

        fun startFeedDetailActivity(
            context: Context,
            item: Feed?,
            category: String?
        ) {
            val intent = Intent(context, FeedDetailActivity::class.java)
            intent.putExtra(KEY_FEED, item)
            intent.putExtra(KEY_CATEGORY, category)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val feed: Feed = intent.getSerializableExtra(KEY_FEED) as Feed
        if (feed == null) {
            finish()
            return
        }

        viewHandler = if (feed.itemType === Feed.TYPE_IMAGE_TEXT) {
            ImageViewHandler(this)
        } else {
            VideoViewHandler(this)
        }

        viewHandler.bindInitData(feed)
    }

}