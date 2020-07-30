package com.cqs.ysa.ws;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by bingo on 2020/7/30 0030.
 * OkHttp WebSocket  不熟悉监听当前状态
 * @deprecated
 */
public class WebSocketHelper {
    public static final String TAG = "webSocket";
    private static WebSocketHelper helper;
    private WebSocket mWebSocket;
    private OkHttpClient okHttpClient;
    //设置读取超时时间
    private int readTimeout = 40;
    //设置写的超时时间
    private int writeTimeout = 40;
    //设置连接超时时间
    private int connectTimeout = 40;
    //设置 PING 帧发送间隔 (开启心跳检测)
    private int pingInterval = 40;
    private WebSocketHelper(){}
    public static WebSocketHelper get(){
        if (helper == null){
            synchronized (WebSocketHelper.class){
                helper = new WebSocketHelper();
            }
        }
        return helper;
    }

    /**
     * 通过ip 端口连接
     * @param hostName
     * @param port
     */
    public void init(String hostName,int port){
        okHttpClient = getHttpClient();
        Request request = new Request.Builder().url("ws://" + hostName + ":" + port).build();
        okHttpClient.newWebSocket(request, new EchoWebSocketListener());
        //okHttpClient.dispatcher().executorService().shutdown();
    }

    /**
     * 初始化webSocket
     * @param domain 通过域名连接
     */
    public void init(String domain){
        okHttpClient = getHttpClient();
        Request request = new Request.Builder().url("ws://" + domain).build();
        okHttpClient.newWebSocket(request,new EchoWebSocketListener());
    }

    /**
     * 获取OkHttpClient 实例
     * @return
     */
    private OkHttpClient getHttpClient(){
        if (okHttpClient == null){
            okHttpClient= new OkHttpClient.Builder()
                    .readTimeout(readTimeout, TimeUnit.SECONDS)
                    .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                    .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .pingInterval(pingInterval, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
        }
        return okHttpClient;
    }

    private class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            mWebSocket = webSocket;
//            String openid = "1";
            //连接成功后，发送登录信息
//            String message = sendData();
//            mSocket.send(message);
            Log.e(TAG,"连接成功！");
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            Log.e(TAG,"receive bytes:" + bytes.hex());
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            Log.e(TAG,"服务器端发送来的信息：" + text);
            //Log.e(TAG,text)
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            Log.e(TAG,"closed:" + reason);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            Log.e(TAG,"closing:" + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            Log.e(TAG,"failure:" + t.getMessage());
        }
    }

    /**
     * 发送消息
     */
    public void sendData() {
        if (mWebSocket != null){
            Map<String,Object> map =new HashMap<>();
            map.put("qrCode", "123456") ;
            JSONObject jsonObject = new JSONObject(map);
            String message = jsonObject.toString() ;
            mWebSocket.send(message);
        }
    }
}
