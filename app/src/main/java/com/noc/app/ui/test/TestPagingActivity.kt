package com.noc.app.ui.test

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.noc.app.R
import com.noc.app.adapters.TestPagingAdapter
import com.noc.app.data.TestStudent
import com.noc.app.databinding.ActivityTestPagingBinding
import com.noc.app.viewmodels.CustomViewModelProvider
import com.noc.app.viewmodels.TestPagingViewModel
import com.noc.lib_common_ui.base.BaseActivity

class TestPagingActivity : BaseActivity() {

    private lateinit var testPagingAdapter: TestPagingAdapter

    // 简单写法
    private val viewModel by viewModels<TestPagingViewModel>()
//    private val viewModel: TestPagingViewModel by viewModels {
//        CustomViewModelProvider.providerTestPagingViewModelFactory()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityTestPagingBinding>(
            this,
            R.layout.activity_test_paging
        )

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        testPagingAdapter = TestPagingAdapter()
        binding.recyclerView.adapter = testPagingAdapter
        // 鞋子数据更新的通知
        viewModel.allStudents.observe(this, Observer {
            it?.let {
                testPagingAdapter.submitList(it)
            }
        })

        binding.buttonClear.setOnClickListener {

        }

        binding.buttonCreate.setOnClickListener {

        }

    }


}