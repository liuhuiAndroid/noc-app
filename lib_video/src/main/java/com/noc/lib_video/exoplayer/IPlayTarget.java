package com.noc.lib_video.exoplayer;

import android.view.ViewGroup;

public interface IPlayTarget {

    ViewGroup getOwner();

    // 活跃状态，视频可播放
    void onActive();

    // 非活跃状态，暂停它
    void inActive();

    // 是否正在播放
    boolean isPlaying();
}
