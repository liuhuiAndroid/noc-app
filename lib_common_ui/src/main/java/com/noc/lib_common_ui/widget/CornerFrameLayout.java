package com.noc.lib_common_ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.noc.lib_common.utilities.ViewHelper;

/**
 * 自定义View 实现圆角功能
 */
public class CornerFrameLayout extends FrameLayout {
    public CornerFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public CornerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CornerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        ViewHelper.setViewOutline(this, attrs, defStyleAttr, defStyleRes);
    }

    public void setViewOutline(int radius, int radiusSide) {
        ViewHelper.setViewOutline(this, radius, radiusSide);
    }

}
