package com.noc.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.noc.app.data.TestStudent

class TestPagingViewModel : ViewModel() {

    val allStudents: LiveData<PagedList<TestStudent>> =
        LivePagedListBuilder(
            TestStudentDataSourceFactory(), PagedList.Config.Builder()
                .setPageSize(4) // 分页加载的数量
                .setEnablePlaceholders(false) // 当item为null是否使用PlaceHolder展示
                .setInitialLoadSizeHint(4) // 预加载的数量
                .build()
        ).build()

    inner class TestStudentDataSourceFactory : DataSource.Factory<Int, TestStudent>() {
        override fun create(): DataSource<Int, TestStudent> {
            return getDataSource()
        }
    }

    private fun getDataSource(): DataSource<Int, TestStudent> {
        return object : PositionalDataSource<TestStudent>() {
            override fun loadRange(
                params: LoadRangeParams,
                callback: LoadRangeCallback<TestStudent>
            ) {
                var list = mutableListOf<TestStudent>()
                list.add(TestStudent(1, "测试"))
                list.add(TestStudent(1, "测试"))
                list.add(TestStudent(1, "测试"))
                callback.onResult(list)
            }

            override fun loadInitial(
                params: LoadInitialParams,
                callback: LoadInitialCallback<TestStudent>
            ) {
                var list = mutableListOf<TestStudent>()
                list.add(TestStudent(1, "测试"))
                list.add(TestStudent(1, "测试"))
                list.add(TestStudent(1, "测试"))
                callback.onResult(list, 0)
            }
        }
    }

}