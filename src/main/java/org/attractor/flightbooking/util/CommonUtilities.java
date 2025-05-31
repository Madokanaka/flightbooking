package org.attractor.flightbooking.util;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtilities {
    private CommonUtilities() {
    }

    public static String getSiteUrl(HttpServletRequest request) {
        String siteUrl = request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(), "");
    }
}
