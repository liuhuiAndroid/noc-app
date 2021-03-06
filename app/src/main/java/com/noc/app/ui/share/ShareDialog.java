package com.noc.app.ui.share;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.noc.app.R;
import com.noc.lib_common.utilities.PixUtils;
import com.noc.lib_common.utilities.ViewHelper;
import com.noc.lib_common_ui.widget.CornerFrameLayout;
import com.noc.lib_image_loader.widget.PPImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 分享面板
 */
public class ShareDialog extends AlertDialog {

    List<ResolveInfo> shareitems = new ArrayList<>();
    private ShareAdapter shareAdapter;
    private String shareContent;
    private View.OnClickListener mListener;
    private CornerFrameLayout layout;
    private Context context;

    public ShareDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        // 自定义View
        layout = new CornerFrameLayout(getContext());
        layout.setBackgroundColor(Color.WHITE);
        layout.setViewOutline(PixUtils.dp2px(context,20), ViewHelper.RADIUS_TOP);

        RecyclerView gridView = new RecyclerView(getContext());
        gridView.setLayoutManager(new GridLayoutManager(getContext(), 4));

        shareAdapter = new ShareAdapter();
        gridView.setAdapter(shareAdapter);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = PixUtils.dp2px(context,20);

        params.leftMargin = params.topMargin = params.rightMargin = params.bottomMargin = margin;
        params.gravity = Gravity.CENTER;
        layout.addView(gridView, params);

        setContentView(layout);
        // 从底部显示
        getWindow().setGravity(Gravity.BOTTOM);
        // 设置透明背景
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置尺寸
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        queryShareItems();
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public void setShareItemClickListener(View.OnClickListener listener) {
        mListener = listener;
    }

    /**
     * 查询文本类型分享所有的入口
     */
    private void queryShareItems() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        // 分享内容为文本类型
        intent.setType("text/plain");

        List<ResolveInfo> resolveInfos = getContext().getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String packageName = resolveInfo.activityInfo.packageName;
            // 只有微信和手机QQ的才保留
            if (TextUtils.equals(packageName, "com.tencent.mm") || TextUtils.equals(packageName, "com.tencent.mobileqq")) {
                shareitems.add(resolveInfo);
            }
        }
        shareAdapter.notifyDataSetChanged();
    }

    private class ShareAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final PackageManager packageManager;

        public ShareAdapter() {
            packageManager = getContext().getPackageManager();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_share_item, parent, false);
            return new RecyclerView.ViewHolder(inflate) {};
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ResolveInfo resolveInfo = shareitems.get(position);
            PPImageView imageView = holder.itemView.findViewById(R.id.share_icon);
            Drawable drawable = resolveInfo.loadIcon(packageManager);
            imageView.setImageDrawable(drawable);

            TextView shareText = holder.itemView.findViewById(R.id.share_text);
            shareText.setText(resolveInfo.loadLabel(packageManager));

            holder.itemView.setOnClickListener(v -> {
                String pkg = resolveInfo.activityInfo.packageName;
                String cls = resolveInfo.activityInfo.name;
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.setComponent(new ComponentName(pkg, cls));
                intent.putExtra(Intent.EXTRA_TEXT, shareContent);

                getContext().startActivity(intent);

                if (mListener != null) {
                    mListener.onClick(v);
                }

                dismiss();
            });
        }

        @Override
        public int getItemCount() {
            return shareitems == null ? 0 : shareitems.size();
        }
    }
}
