package com.xingkong.sl.starmessage;

/**
 * Created by SeaLynn0 on 2018/1/11.
 */

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
