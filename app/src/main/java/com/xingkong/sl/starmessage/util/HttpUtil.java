package com.xingkong.sl.starmessage.util;

import android.util.Log;

import com.xingkong.sl.starmessage.HttpCallbackListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by SeaLynn0 on 2018/1/11.
 */

/**
 * 处理网络请求的工具类
 */
public class HttpUtil {

    public static void sendHttpRequest(final String adress, final String requestMethod, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(adress);
                    conn = (HttpURLConnection) url.openConnection();
                    //Http请求的方法，GET表示希望从服务器哪里获得数据
                    conn.setRequestMethod(requestMethod);
                    //连接超时
                    conn.setConnectTimeout(8000);
                    //读取超时
                    conn.setReadTimeout(8000);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    InputStream in = conn.getInputStream();
                    //下面对获取到的输入流进行读取
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String data;
                    while ((data = reader.readLine()) != null) {
                        response.append(data);
                    }
                    //在控制台用log把获得的数据打印出来
                    Log.d(TAG, "run: " + response);
                    if (listener != null) {
                        //回调onFinish()方法
                        listener.onFinish(response.toString());
                    }
                } catch (IOException e) {
                    if (listener != null) {
                        //回调onError()方法
                        listener.onError(e);
                    }
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        //用disconnect()关掉Http连接
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }
}
