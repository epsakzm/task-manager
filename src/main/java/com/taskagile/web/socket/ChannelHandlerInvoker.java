package com.taskagile.web.socket;

import com.taskagile.utils.JsonUtils;
import io.jsonwebtoken.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ChannelHandlerInvoker {

    private static final Logger log = LoggerFactory.getLogger(ChannelHandlerInvoker.class);

    private static final AntPathMatcher antpathMatcher = new AntPathMatcher();

    private String channelPattern;
    private Object handler;

    private final Map<String, Method> actionMethods = new HashMap<>();

    public ChannelHandlerInvoker(Object handler) {
        Assert.notNull(handler, "Parameter `handler` must not be null");

        Class<?> handlerClass = handler.getClass();
        ChannelHandler handlerAnnotation = handlerClass.getAnnotation(ChannelHandler.class);
        Assert.notNull(handlerAnnotation, "Parameter `handler` must have annotation @ChannelHandler");

        Arrays.stream(handlerClass.getMethods()).forEach(method -> {
            Action actionAnnotation = method.getAnnotation(Action.class);
            if (actionAnnotation != null) {
                String action = actionAnnotation.value();
                actionMethods.put(action, method);
                log.debug("Mapped action `{}` in channel Handler `{}#{}`", action, handlerClass.getName(), method);
            }
        });
        this.channelPattern = ChannelHandlers.getPattern(handlerAnnotation);
        this.handler = handler;
    }

    public boolean supports(String action) {
        return actionMethods.containsKey(action);
    }

    public void handle(IncomingMessage incomingMessage, RealTimeSession session) {
        Assert.isTrue(antpathMatcher.match(channelPattern, incomingMessage.getChannel()), "Channel of the handler must match");
        Method actionMethod = actionMethods.get(incomingMessage.getAction());
        Assert.notNull(actionMethod, "Action method for `" + incomingMessage.getAction() + "` must exist");

        Class<?>[] parameterTypes = actionMethod.getParameterTypes();
        Annotation[][] allParameterAnnotations = actionMethod.getParameterAnnotations();
        Object[] args = new Object[parameterTypes.length];

        try {
            for (int i = 0; i < parameterTypes.length; ++i) {
                Class<?> parameterType = parameterTypes[i];
                Annotation[] parameterAnnotations = allParameterAnnotations[i];

                if (parameterAnnotations.length == 0) {
                    if (parameterType.isInstance(session)) {
                        args[i] = session;
                    } else {
                        args[i] = null;
                    }
                }

                Annotation parameterAnnotation = parameterAnnotations[0];
                if (parameterAnnotation instanceof Payload) {
                    Object arg = JsonUtils.toObject(incomingMessage.getPayload(), parameterType);
                    if (arg == null) {
                        throw new IllegalArgumentException("Unable to instantiate parameter of type `" + parameterType.getName() + "`.");
                    }
                    args[i] = arg;
                } else if (parameterAnnotation instanceof ChannelValue) {
                    args[i] = incomingMessage.getChannel();
                }
            }
            actionMethod.invoke(handler, args);
        } catch (Exception e) {
            String error = "Failed to invoker action method `" + incomingMessage.getAction() + "` at channel `" + incomingMessage.getChannel() + "`";
            log.error(error, e);
            session.error(error);
        }
    }
}
