package com.example.springbootwebdemo.reptile.Utils;

import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionUtils {
    /**
     * @param url   get HttpUrlConnection by url
     */
    public static HttpURLConnection getConnection(String url) {
        try {
            URL baseurl=new URL(url);
            return (HttpURLConnection) baseurl.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
