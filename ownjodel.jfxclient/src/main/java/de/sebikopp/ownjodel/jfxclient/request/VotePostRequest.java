package de.sebikopp.ownjodel.jfxclient.request;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import de.sebikopp.ownjodel.jfxclient.connect.CookieHolder;
import de.sebikopp.ownjodel.jfxclient.connect.WebTargetFactory;

public class VotePostRequest {
	public void votePostsAsync(String id, boolean up){
		WebTarget trgt = WebTargetFactory
				.getTargetSinglePost()
				.resolveTemplate("id", id);
		String entity = new StringBuilder("{\"upvote\":").append(up).append("}").toString();
		
		Invocation.Builder bui = trgt.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		List<Cookie> cookies = CookieHolder.getInstance().getAllCookiesAsList();
		for (Cookie co: cookies)
			bui.cookie(co);
		final Future<Response> rsp = bui.async().put(Entity.entity(entity, MediaType.APPLICATION_JSON));
		new Thread(() -> {
			try {
				Response xrsp = rsp.get();
				System.out.println(rsp.get().getEntity());
				for (Map.Entry<String, NewCookie> cookie: xrsp.getCookies().entrySet()){
					CookieHolder.getInstance().add(cookie.getKey(), cookie.getValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
}