package com.noc.lib_network.okhttp2;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class JsonConvert implements Convert {

    //默认的Json转 Java Bean的转换器
    @Override
    public Object convert(String response, Type type) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject data = jsonObject.getJSONObject("data");
            if (data != null) {
                Object data1 = data.get("data");
                Object object = new Gson().fromJson(data1.toString(), type);
                return object;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
