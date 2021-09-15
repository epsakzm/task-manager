package com.taskagile.web.socket;

import com.taskagile.domain.common.security.TokenManager;
import com.taskagile.domain.model.user.UserId;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketRequestDispatcher extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketRequestDispatcher.class);

    private final TokenManager tokenManager;

    public WebSocketRequestDispatcher(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        log.debug("WebSocket connection established");
        RealTimeSession session = new RealTimeSession(webSocketSession);
        String token = session.getToken();

        try {
            UserId userId = tokenManager.verifyJwt(token);
            session.addAttributes("userId", userId);
            session.reply("인증 성공");
        } catch (JwtException e) {
            log.debug("유효하지 않은 토큰입니다.");
            session.fail("인증 실패");
        }
    }
}
