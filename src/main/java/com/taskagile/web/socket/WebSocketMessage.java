package com.taskagile.web.socket;

public class WebSocketMessage {

    private String type;
    private String message;

    public static Object reply(String reply) {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.type = "reply";
        webSocketMessage.message = reply;
        return webSocketMessage;
    }

    public static Object failure(String failure) {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.type = "failure";
        webSocketMessage.message = failure;
        return webSocketMessage;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
