package com.taskagile.web.socket;

import com.taskagile.domain.model.user.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class SubscriptionHub {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionHub.class);
    // Key 는 채널, value 는 subscribe 한 웹 소켓 세션 Set
    private static final Map<String, Set<WebSocketSession>> subscriptions = new HashMap<>();
    // client 가 subscribe 한 채널 유지
    // 키는 세션아이디, 값은 Subscribe 한 채널 Set
    private static final Map<String, Set<String>> subscribedChannels = new HashMap<>();

    public static void subscribe(RealTimeSession session, String channel) {
        Assert.hasText(channel, "Parameter `channel` must not be null");

        Set<WebSocketSession> subscribers = subscriptions.computeIfAbsent(channel, k -> new HashSet<>());
        subscribers.add(session.wrapped());

        UserId userId = session.getUserId();
        log.debug("RealTimeSession[{}] Subscribed user[id={}] to channel `{}`", session.id(), userId, channel);

        Set<String> channels = subscribedChannels.computeIfAbsent(session.id(), k -> new HashSet<>());
        channels.add(channel);
    }

    public static void unsubscribe(RealTimeSession session, String channel) {
        Assert.hasText(channel, "Parameter `channel` must not be empty");
        Assert.notNull(session, "Parameter `session` must not be null");

        Set<WebSocketSession> subscribers = subscriptions.get(channel);
        if (subscribers != null) {
            subscribers.remove(session.wrapped());
            UserId userId = session.getUserId();
            log.debug("RealTimeSession[{}] Unsubscribed user[id={}] from channel `{}`", session.id(), userId, channel);
        }

        Set<String> channels = subscribedChannels.get(session.id());
        if (channels != null) {
            channels.remove(channel);
        }
    }

    public static void unsubscribeAll(RealTimeSession session) {
        Set<String> channels = subscribedChannels.get(session.id());
        if (channels == null) {
            log.debug("RealTimeSession[{}] No channels to unsubscribe", session.id());
            return;
        }
        channels.forEach(channel -> unsubscribe(session, channel));
        log.debug("RealTimeSession[{}] Unsubscribed all channels", session.id());
        subscribedChannels.remove(session.id());
    }

    public static void send(String channel, String update) {
        Assert.hasText(channel, "Parameter `channel` must not be empty");
        Assert.hasText(update, "Parameter `update` must not be empty");

        Set<WebSocketSession> subscribers = subscriptions.get(channel);
        if (subscribers == null || subscriptions.isEmpty()) {
            log.debug("채널 Subscribe 정보가 없습니다.");
            return;
        }
        subscribers.forEach(subscriber -> sendTo(subscriber, channel, update));
    }

    private static void sendTo(WebSocketSession subscriber, String channel, String update) {
        try {
            subscriber.sendMessage(WebSocketMessages.channelMessage(channel, update));
            log.debug("RealTimeSession[{}] Send Message `{}` to subscriber at channel `{}`", subscriber.getId(), update, channel);
        } catch (IOException e) {
            log.error("메시지 전송 실패 subscriber : `{}`, 채널 : `{}`, 메시지 : `{}`", subscriber.getId(), channel, update);
        }
    }
}
