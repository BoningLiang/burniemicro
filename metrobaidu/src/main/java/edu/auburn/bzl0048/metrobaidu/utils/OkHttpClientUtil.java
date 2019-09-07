package edu.auburn.bzl0048.metrobaidu.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkHttpClientUtil {

    OkHttpClient client;

    public OkHttpClientUtil() {

        client = new OkHttpClient();

    }

    public void send() throws IOException {
        Request request = new Request.Builder()
                .url("http://www.baidu.com").build();
        Response response = client.newCall(request).execute();
    }

}
