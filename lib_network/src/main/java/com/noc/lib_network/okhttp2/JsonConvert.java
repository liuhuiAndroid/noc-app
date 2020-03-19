package com.noc.lib_network.okhttp2;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * 默认的Json转Java Bean的转换器
 */
public class JsonConvert implements Convert {

    @Override
    public Object convert(String response, Type type) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject data = jsonObject.getJSONObject("data");
            if (data != null) {
                Object dataObject = data.get("data");
                Object object = new Gson().fromJson(dataObject.toString(), type);
                return object;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
