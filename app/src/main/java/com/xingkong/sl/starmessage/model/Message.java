package com.xingkong.sl.starmessage.model;

/**
 * Created by SeaLynn0 on 2018/1/12.
 */

public class Message {

    private String username;

    private String time;

    private String content;

    public Message(String username,String content,String time){
        this.username = username;
        this.time = time;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }
}
