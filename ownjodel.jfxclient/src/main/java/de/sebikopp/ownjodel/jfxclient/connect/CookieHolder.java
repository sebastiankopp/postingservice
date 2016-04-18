package de.sebikopp.ownjodel.jfxclient.connect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;

public class CookieHolder {
	private Map<String, Cookie> cookies;
	private static CookieHolder instance = null;
	private CookieHolder() {
		cookies = new HashMap<>();
	}
	public static CookieHolder getInstance(){
		if (instance == null)
			instance = new CookieHolder();	
		return instance;
	}
	public synchronized void add(String cookiePk, NewCookie cookie){
		cookies.put(cookiePk, cookie.toCookie());
	}
	public synchronized List<Cookie> getAllCookiesAsList(){
		return new ArrayList<Cookie>(cookies.values());
	}
}
