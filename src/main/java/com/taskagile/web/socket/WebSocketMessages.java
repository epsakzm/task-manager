package com.taskagile.web.socket;

import com.taskagile.utils.JsonUtils;
import org.springframework.web.socket.TextMessage;

import java.util.HashMap;

public class WebSocketMessages {

    public static TextMessage reply(String reply) {
        HashMap<String, String> messageObject = new HashMap<>();
        messageObject.put("type", "reply");
        messageObject.put("message", reply);
        return new TextMessage(JsonUtils.toJson(messageObject));
    }

    public static TextMessage error(String error) {
        HashMap<String, String> messageObject = new HashMap<>();
        messageObject.put("type", "error");
        messageObject.put("message", error);
        return new TextMessage(JsonUtils.toJson(messageObject));
    }

    public static TextMessage failure(String failure) {
        HashMap<String, String> messageObject = new HashMap<>();
        messageObject.put("type", "failure");
        messageObject.put("message", failure);
        return new TextMessage(JsonUtils.toJson(messageObject));
    }

    public static TextMessage channelMessage(String channel, String payload) {
        HashMap<String, String> messageObject = new HashMap<>();
        messageObject.put("channel", channel);
        messageObject.put("message", payload);
        return new TextMessage(JsonUtils.toJson(messageObject));
    }
}
