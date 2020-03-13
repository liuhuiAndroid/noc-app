package com.noc.lib_common_ui.utilities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 第二种，有时间比较一下
 */
public class StatusBarUtil2 {

    /**
     * 6.0级以上的沉浸式布局
     *
     * @param activity
     */
    public static void fitSystemBar(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        Window window = activity.getWindow();
        View decorView = window.getDecorView();
        // View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN--能够使得我们的页面布局延伸到状态栏之下，但不会隐藏状态栏。也就相当于状态栏是遮盖在布局之上的
        // View.SYSTEM_UI_FLAG_FULLSCREEN -- 能够使得我们的页面布局延伸到状态栏，但是会隐藏状态栏。
        // WindowManager.LayoutParams.FLAG_FULLSCREEN
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 指定状态栏的背景色
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * 6.0及以上的状态栏色调
     *
     * @param activity
     * @param light    true:白底黑字,false:黑底白字
     */
    public static void lightStatusBar(Activity activity, boolean light) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        Window window = activity.getWindow();
        View decorView = window.getDecorView();
        int visibility = decorView.getSystemUiVisibility();
        if (light) {
            visibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            visibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        decorView.setSystemUiVisibility(visibility);
    }

}
