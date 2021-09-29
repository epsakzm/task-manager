package com.taskagile.web.socket;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ChannelValue {
}
