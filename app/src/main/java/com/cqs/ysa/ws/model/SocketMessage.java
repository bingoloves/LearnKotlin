package com.cqs.ysa.ws.model;

/**
 * Created by Administrator on 2020/7/30 0030.
 * 接收服务端来的消息
 */

public class SocketMessage {
    //消息的类型
    public int type;
    //消息的说明(例如：一些错误说明)
    public String msg;
    //数据体
    public String data;
}
