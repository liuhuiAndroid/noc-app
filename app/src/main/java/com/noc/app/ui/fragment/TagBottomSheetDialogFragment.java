package com.noc.app.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.noc.app.R;
import com.noc.app.data.bean.TagList;
import com.noc.app.ui.activity.UserManager;
import com.noc.lib_common.utilities.PixUtils;
import com.noc.lib_network.okhttp2.ApiResponse;
import com.noc.lib_network.okhttp2.ApiService;
import com.noc.lib_network.okhttp2.JsonCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择标签
 */
public class TagBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private TagsAdapter tagsAdapter;
    private List<TagList> mTagLists = new ArrayList<>();
    private OnTagItemSelectedListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_tag_bottom_sheet_dialog, null, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tagsAdapter = new TagsAdapter();
        recyclerView.setAdapter(tagsAdapter);

        dialog.setContentView(view);
        ViewGroup parent = (ViewGroup) view.getParent();
        BottomSheetBehavior<ViewGroup> behavior = BottomSheetBehavior.from(parent);
        // 设置默认高度
        behavior.setPeekHeight(PixUtils.getScreenHeight() / 3);
        behavior.setHideable(false);

        ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
        layoutParams.height = PixUtils.getScreenHeight() / 3 * 2;
        parent.setLayoutParams(layoutParams);

        queryTagList();
        return dialog;
    }

    /**
     * 查询TAG列表
     */
    private void queryTagList() {
        ApiService.get("/tag/queryTagList")
                .addParam("userId", UserManager.get().getUserId())
                .addParam("pageCount", 100)
                .addParam("tagId", 0).execute(new JsonCallback<List<TagList>>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccess(ApiResponse<List<TagList>> response) {
                super.onSuccess(response);
                if (response.body != null) {
                    List<TagList> list = response.body;
                    mTagLists.addAll(list);
                    // 切换到主线程
                    ArchTaskExecutor.getMainThreadExecutor().execute(() ->
                            tagsAdapter.notifyDataSetChanged());
                }
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onError(ApiResponse<List<TagList>> response) {
                ArchTaskExecutor.getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), response.message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void setOnTagItemSelectedListener(OnTagItemSelectedListener listener) {
        this.listener = listener;
    }

    class TagsAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(13);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.color_000));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setLayoutParams(new RecyclerView.LayoutParams(-1, PixUtils.dp2px(45)));

            return new RecyclerView.ViewHolder(textView) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            TagList tagList = mTagLists.get(position);
            textView.setText(tagList.title);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onTagItemSelected(tagList);
                        dismiss();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mTagLists.size();
        }
    }

    public interface OnTagItemSelectedListener {
        void onTagItemSelected(TagList tagList);
    }

}
