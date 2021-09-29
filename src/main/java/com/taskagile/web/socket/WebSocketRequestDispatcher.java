package com.taskagile.web.socket;

import com.taskagile.domain.common.security.TokenManager;
import com.taskagile.domain.model.user.UserId;
import com.taskagile.utils.JsonUtils;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketRequestDispatcher extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketRequestDispatcher.class);

    private final TokenManager tokenManager;
    private final ChannelHandlerResolver channelHandlerResolver;

    public WebSocketRequestDispatcher(TokenManager tokenManager, ChannelHandlerResolver channelHandlerResolver) {
        this.tokenManager = tokenManager;
        this.channelHandlerResolver = channelHandlerResolver;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        log.debug("WebSocket connection established");
        RealTimeSession session = new RealTimeSession(webSocketSession);
        String token = session.getToken();

        try {
            UserId userId = tokenManager.verifyJwt(token);
            session.setUserId(userId);
            session.reply("인증 성공");
        } catch (JwtException e) {
            log.debug("유효하지 않은 토큰입니다.");
            session.fail("인증 실패");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        RealTimeSession realTimeSession = new RealTimeSession(session);
        log.debug("RealTimeSession[{}] Received message `{}`", realTimeSession.id(), message.getPayload());

        IncomingMessage incomingMessage = JsonUtils.toObject(message.getPayload(), IncomingMessage.class);
        if (incomingMessage == null) {
            realTimeSession.error("메시지 포맷이 유효하지 않습니다 !" + message.getPayload());
            return;
        }

        ChannelHandlerInvoker invoker = channelHandlerResolver.findInvoker(incomingMessage);
        if (invoker == null) {
            String errorMessage = "핸들러를 찾을 수 없습니다. Action : {}" + incomingMessage.getAction() + ", Channel : " + incomingMessage.getChannel();
            realTimeSession.error(errorMessage);
            log.error("RealTimeSession[{}] {}", realTimeSession.id(), errorMessage);
            return;
        }
        invoker.handle(incomingMessage, realTimeSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        RealTimeSession realTimeSession = new RealTimeSession(session);
        SubscriptionHub.unsubscribeAll(realTimeSession);
    }
}
