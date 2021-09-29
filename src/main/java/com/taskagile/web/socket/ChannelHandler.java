package com.taskagile.web.socket;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ChannelHandler {

    /**
     * 채널 패턴
     */
    String pattern() default "";

    /**
     * Handler 가 WebSocketRequestDispatcher 에 의해 매핑될 채널 패턴
     * {@link org.springframework.util.AntPathMatcher}
     */
    String value() default "";
}
