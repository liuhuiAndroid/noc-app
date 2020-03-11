package com.noc.app.api;

import com.noc.app.data.User;
import com.noc.app.data.bean.Feed;
import com.noc.lib_network.okhttp.CommonOkHttpClient;
import com.noc.lib_network.okhttp.listener.DisposeDataHandle;
import com.noc.lib_network.okhttp.listener.DisposeDataListener;
import com.noc.lib_network.okhttp.request.CommonRequest;
import com.noc.lib_network.okhttp.request.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求中心
 */
public class RequestCenter {

    static class HttpConstants {

        private static final String ROOT_URL = "http://123.56.232.18:8080/serverdemo";

        // 首页请求接口
        private static String HOT_FEEDS_LIST = ROOT_URL + "/feeds/queryHotFeedsList";

        // 登陆接口
        public static String LOGIN = ROOT_URL + "/module_voice/login_phone";
    }

    //根据参数发送所有get请求
    public static void getRequest(String url, RequestParams params, DisposeDataListener listener,
                                  Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, params),
                new DisposeDataHandle(listener, clazz));
    }

    //根据参数发送所有post请求
    public static void postRequest(String url, RequestParams params, DisposeDataListener listener,
                                   Class<?> clazz) {
        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, params),
                new DisposeDataHandle(listener, clazz));
    }

//    public static void requestRecommandData(DisposeDataListener listener) {
//        RequestCenter.getRequest(HttpConstants.HOME_RECOMMAND, null, listener,
//                BaseRecommandModel.class);
//    }
//
//    public static void requestRecommandMore(DisposeDataListener listener) {
//        RequestCenter.getRequest(HttpConstants.HOME_RECOMMAND_MORE, null, listener,
//                BaseRecommandMoreModel.class);
//    }
//
//    public static void requestFriendData(DisposeDataListener listener) {
//        RequestCenter.getRequest(HttpConstants.HOME_FRIEND, null, listener, BaseFriendModel.class);
//    }

    /**
     * 用户登陆请求
     */
    public static void login(DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("mb", "18734924592");
        params.put("pwd", "999999q");
        RequestCenter.getRequest(HttpConstants.LOGIN, params, listener, User.class);
    }

    /**
     * 首页请求
     */
    public static void hotFeedsList(String feedType, long userId, int feedId, int count, DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("feedType", feedType);
        params.put("userId", userId + "");
        params.put("feedId", feedId + "");
        params.put("pageCount", count + "");
        RequestCenter.getRequest(HttpConstants.HOT_FEEDS_LIST, params, listener, null);
    }
}
