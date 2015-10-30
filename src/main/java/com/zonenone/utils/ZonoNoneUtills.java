package com.zonenone.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.servlet.http.HttpServletRequest;

public class ZonoNoneUtills {
	private static final String[] HEADERS_TO_TRY = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	public static String getClientIpAddress(HttpServletRequest request) {
		for (String header : HEADERS_TO_TRY) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}

	public static String getUserAgent(HttpServletRequest req) {
		return req.getHeader("user-agent");
	}

	public static String getBrowserType(String userAgent) {
		String browserType = "";
		if (userAgent.contains("Chrome Mobile")) {
			browserType = "Chrome Mobile";
		} else if (userAgent.contains("Firefox")) {
			browserType = "Firefox";
		} else if (userAgent.contains("MSIE")) {
			browserType = "Internet Explorer";
		} else if (userAgent.contains("Safari")) {
			browserType = "Safari";
		} else if (userAgent.contains("Chrome")) {
			browserType = "Chrome";
		} else if (userAgent.contains("Chromium")) {
			browserType = "Chromium";
		} else if (userAgent.contains("Mozilla")) {
			browserType = "Mobile Safari";
		} else if (userAgent.contains("Opera Mini")) {
			browserType = "Opera Mini";
		} else if (userAgent.contains("Opera")) {
			browserType = "Opera";
		}
		return browserType;
	}

	public static Double getHeight() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// the screen height
		return screenSize.getHeight();

	}

	public static Double getWidth() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// the screen width
		return screenSize.getWidth();
	}
}
