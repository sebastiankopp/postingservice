package de.sebikopp.ownjodel.jfxclient.connect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;

public class CookieHolder {
	private Map<String, Cookie> cookies;
	private CookieHolder() {
		cookies = new HashMap<>();
	}
	public static CookieHolder getInstance(){
		return new CookieHolder();
	}
	public synchronized void add(String bla, NewCookie cookie){
		cookies.put(bla, cookie.toCookie());
	}
	public synchronized List<Cookie> getAllCookiesAsList(){
		return new ArrayList<Cookie>(cookies.values());
	}
}
