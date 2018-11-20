package com.xingkong.sl.starmessage.util;

import android.util.Log;

import com.xingkong.sl.starmessage.model.Message;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by SeaLynn0 on 2018/1/13.
 */

public class DataUtil {

    public static void parseDataToArray(String Data, List<Message> mItemList) {
        mItemList.clear();
        String[] sourceArray = Data.split("<br>");
        for (int i = 0; i < sourceArray.length; i++) {
//            System.out.println(sourceArray[i]);
            String[] rawItem = sourceArray[i].split("\\[s]");
            for (int j = 0;j<rawItem.length;j++){
                Log.d(TAG, "parseDataToArray: " + rawItem[j]);
            }

            Message msg = new Message(rawItem[0], rawItem[1], rawItem[2]);
            mItemList.add(msg);
        }
    }
}