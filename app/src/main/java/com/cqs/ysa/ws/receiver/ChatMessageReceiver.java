package com.cqs.ysa.ws.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cqs.ysa.ws.model.ChatMessage;

/**
 * Created by bingo on 2020/7/30 0030.
 * 消息接收后 使用广播传递数据
 */

public class ChatMessageReceiver extends BroadcastReceiver {
    public static final String ACTION_CHAT_MESSAGE = "com.im.chat.message";
    private ChatMessageListener chatMessageListener;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ACTION_CHAT_MESSAGE.equals(intent.getAction())){
            String message= intent.getStringExtra("message");
            ChatMessage chatMessage= new ChatMessage();
            chatMessage.setContent(message);
            chatMessage.setIsMeSend(0);
            chatMessage.setIsRead(1);
            chatMessage.setTime(System.currentTimeMillis()+"");
            if (chatMessageListener != null){
                chatMessageListener.onReceive(chatMessage);
            }
        }
    }

    public interface ChatMessageListener{
        void onReceive(ChatMessage message);
    }

    public void setChatMessageListener(ChatMessageListener chatMessageListener) {
        this.chatMessageListener = chatMessageListener;
    }
}
