package com.noc.lib_network.okhttp2;

import java.lang.reflect.Type;

public interface Convert<T> {

    T convert(String response, Type type);

}
