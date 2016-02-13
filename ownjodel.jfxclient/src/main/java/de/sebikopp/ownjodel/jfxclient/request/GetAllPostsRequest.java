package de.sebikopp.ownjodel.jfxclient.request;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import de.sebikopp.ownjodel.jfxclient.connect.CookieHolder;
import de.sebikopp.ownjodel.jfxclient.connect.WebTargetFactory;
import de.sebikopp.ownjodel.jfxclient.model.PostFromServer;

public class GetAllPostsRequest {

	public List<PostFromServer> getAllPosts() throws IOException{
		long start = System.currentTimeMillis();
		try {
			WebTarget allPostsTrgt = WebTargetFactory.getTargetAllPosts();
			Invocation.Builder bui = allPostsTrgt.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			List<Cookie> cookies = CookieHolder.getInstance().getAllCookiesAsList();
			for (Cookie co: cookies)
				bui.cookie(co);
			Response rsp = bui.buildGet().invoke();
			String resp = rsp.readEntity(String.class);
			for (Map.Entry<String, NewCookie> cookie: rsp.getCookies().entrySet()){
				CookieHolder.getInstance().add(cookie.getKey(), cookie.getValue());
			}
			System.out.println(resp);
			return JsonConverterPostFromServer.convertPostListFromServer(resp);
		} finally {
			System.out.println("Needed " + (System.currentTimeMillis()-start)+" ms for getAllPosts()");
			WebTargetFactory.releaseClient();
		}
	}
}
