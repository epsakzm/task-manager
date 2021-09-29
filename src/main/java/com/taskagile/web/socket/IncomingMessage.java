package com.taskagile.web.socket;

/**
 * 웹소켓을 통해 받는 메시지. JSON 포맷.
 * <pre>
 * {
 *     "channel": required|String,
 *     "action: required|String,
 *     "payload": required|String
 * }
 * </pre>
 */
public class IncomingMessage {

    /**
     * {@link WebSocketRequestDispatcher} will route the request to the corresponding {@link ChannelHandler}.
     */
    private String channel;

    /**
     * {@link WebSocketRequestDispatcher} will route the request to the corresponding
     * action method by checking the {@link Action}.
     */
    private String action;

    /**
     * The payload of hte message that an action method will receive as its input.
     */
    private String payload;

    public static IncomingMessage create(String channel, String action, String payload) {
        IncomingMessage incomingMessage = new IncomingMessage();
        incomingMessage.channel = channel;
        incomingMessage.action = action;
        incomingMessage.payload = payload;
        return incomingMessage;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
