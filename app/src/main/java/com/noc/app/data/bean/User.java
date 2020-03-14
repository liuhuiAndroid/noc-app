package com.noc.app.data.bean;

import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import java.io.Serializable;

/**
 * 用户信息
 */
public class User extends BaseObservable implements Serializable {

    public int id;
    public long userId;
    public String name;
    public String avatar;
    public String description;
    public int likeCount;
    public int topCommentCount;
    public int followCount;
    public int followerCount;
    public String qqOpenId;
    public long expires_time;
    public int score;
    public int historyCount;
    public int commentCount;
    public int favoriteCount;
    public int feedCount;
    public boolean hasFollow;

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || !(obj instanceof Feed))
            return false;
        User newUser = (User) obj;
        return TextUtils.equals(name, newUser.name)
                && TextUtils.equals(avatar, newUser.avatar)
                && TextUtils.equals(description, newUser.description)
                && likeCount == newUser.likeCount
                && topCommentCount == newUser.topCommentCount
                && followCount == newUser.followCount
                && followerCount == newUser.followerCount
                && qqOpenId == newUser.qqOpenId
                && expires_time == newUser.expires_time
                && score == newUser.score
                && historyCount == newUser.historyCount
                && commentCount == newUser.commentCount
                && favoriteCount == newUser.favoriteCount
                && feedCount == newUser.feedCount
                && hasFollow == newUser.hasFollow;
    }

    @Bindable
    public boolean isHasFollow() {
        return hasFollow;
    }

    public void setHasFollow(boolean hasFollow) {
        this.hasFollow = hasFollow;
        notifyPropertyChanged(com.noc.app.BR._all);
    }

}
