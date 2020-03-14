package com.noc.lib_common_ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.noc.lib_common.utilities.PixUtils;
import com.noc.lib_common.utilities.ViewHelper;
import com.noc.lib_common_ui.R;

public class LoadingDialog extends AlertDialog {

    private TextView loadingText;
    private Context context;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    protected LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public void setLoadingText(String loadingText) {
        if (this.loadingText != null) {
            this.loadingText.setText(loadingText);
        }
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.layout_loading_view);
        loadingText = findViewById(R.id.loading_text);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.gravity = Gravity.CENTER;
        attributes.dimAmount = 0.5f;
        //这个背景必须设置哦，否则 会出现对话框 宽度很宽
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //给转菊花的loading对话框来一个圆角
        ViewHelper.setViewOutline(findViewById(R.id.loading_layout),
                PixUtils.dp2px(context, 10), ViewHelper.RADIUS_ALL);
        window.setAttributes(attributes);
    }

}
