package com.taskagile.web.socket;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {

    /**
     * 액션 패턴. 정확히 일치채야 함
     * <p>e.g. "subscribe", "unsubscribe"</p>
     */
    String value() default "";
}
