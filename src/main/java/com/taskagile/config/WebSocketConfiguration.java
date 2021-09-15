package com.taskagile.config;

import com.taskagile.web.socket.WebSocketRequestDispatcher;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private WebSocketRequestDispatcher webSocketRequestDispatcher;

    public WebSocketConfiguration(WebSocketRequestDispatcher webSocketRequestDispatcher) {
        this.webSocketRequestDispatcher = webSocketRequestDispatcher;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketRequestDispatcher, "/rt").setAllowedOrigins("*").withSockJS();
    }

}
