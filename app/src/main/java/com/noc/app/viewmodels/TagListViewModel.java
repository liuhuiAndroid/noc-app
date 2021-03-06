package com.noc.app.viewmodels;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.ItemKeyedDataSource;

import com.google.gson.reflect.TypeToken;
import com.noc.app.data.bean.TagList;
import com.noc.app.ui.AbsViewModel;
import com.noc.app.ui.activity.UserManager;
import com.noc.lib_network.okhttp2.ApiResponse;
import com.noc.lib_network.okhttp2.ApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class TagListViewModel extends AbsViewModel<TagList> {

    private String tagType;
    private int offset;
    private AtomicBoolean loadAfter = new AtomicBoolean();
    private MutableLiveData switchTabLiveData = new MutableLiveData();

    @Override
    public DataSource createDataSource() {
        return new DataSource();
    }

    public MutableLiveData getSwitchTabLiveData() {
        return switchTabLiveData;
    }

    /**
     * 方便设置
     */
    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public class DataSource extends ItemKeyedDataSource<Long, TagList> {

        @Override
        public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<TagList> callback) {
            loadData(0L, callback);
        }

        @Override
        public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<TagList> callback) {
            loadData(params.key, callback);
        }

        @Override
        public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<TagList> callback) {
            callback.onResult(Collections.emptyList());
        }

        public void loadData(Long requestKey, LoadCallback<TagList> callback) {
            if (requestKey > 0) {
                loadAfter.set(true);
            }
            ApiResponse<List<TagList>> response = ApiService.get("/tag/queryTagList")
                    .addParam("userId", UserManager.get().getUserId())
                    .addParam("tagId", requestKey)
                    .addParam("tagType", tagType)
                    .addParam("pageCount", 10)
                    .addParam("offset", offset)
                    .responseType(new TypeToken<ArrayList<TagList>>() {
                    }.getType())
                    .execute();

            List<TagList> result = response.body == null ? Collections.emptyList() : response.body;
            callback.onResult(result);
            if (requestKey > 0) {
                loadAfter.set(false);
                offset += result.size();
                ((MutableLiveData) getBoundaryPageData()).postValue(result.size() > 0);
            } else {
                offset = result.size();
            }
        }

        @NonNull
        @Override
        public Long getKey(@NonNull TagList item) {
            return item.tagId;
        }
    }

    @SuppressLint("RestrictedApi")
    public void loadData(long tagId, ItemKeyedDataSource.LoadCallback callback) {
        if (tagId <= 0 || loadAfter.get()) {
            callback.onResult(Collections.emptyList());
            return;
        }
        ArchTaskExecutor.getIOThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ((TagListViewModel.DataSource) getDataSource()).loadData(tagId, callback);
            }
        });
    }

}
