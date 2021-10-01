package com.taskagile.utils;

import com.taskagile.domain.common.model.IpAddress;

import javax.servlet.http.HttpServletRequest;

public final class RequestUtils {

    public static final String X_FORWARDED_FOR = "X-FORWARDED-FOR";

    private RequestUtils() {}

    public static IpAddress getIpAddress(HttpServletRequest request) {
        String remoteAddress = request.getRemoteAddr();

        if (request.getHeader(X_FORWARDED_FOR) != null) {
            return new IpAddress(substringX(request.getHeader(X_FORWARDED_FOR)));
        }
        return new IpAddress(remoteAddress);
    }

    private static String substringX(String forwarded) {
        int index = forwarded.indexOf(',');
        if (index > -1) {
            return forwarded.substring(0, index);
        }
        return forwarded;
    }
}
