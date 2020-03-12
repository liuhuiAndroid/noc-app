package com.noc.lib_network.okhttp2;

public class ApiResponse<T> {
    public boolean success;
    public int status;
    public String message;
    public T body;
}
