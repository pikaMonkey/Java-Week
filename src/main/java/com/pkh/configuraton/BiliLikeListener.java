package com.pkh.configuraton;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class BiliLikeListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = message.toString();
        String channel = new String(message.getChannel());
        System.out.println("channel: " + channel + "  msg: " + msg);
    }
}
