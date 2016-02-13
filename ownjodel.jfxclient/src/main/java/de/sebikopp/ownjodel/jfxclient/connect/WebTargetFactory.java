package de.sebikopp.ownjodel.jfxclient.connect;

import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

public class WebTargetFactory {
	private static final String PROP_HOST = "host";
	private static final String PROPS_FILE = "props/connect.properties";
	private static final String PROP_PORT = "port";
	private static String host;
	private static int port;
//	private static Client client;
	
	static {
		try {
			ClassLoader cl = WebTargetFactory.class.getClassLoader();
			InputStream is = cl.getResourceAsStream(PROPS_FILE);
			Properties props = new Properties();
			props.load(is);
			host = props.getProperty(PROP_HOST).trim();
			port = Integer.parseInt(props.getProperty(PROP_PORT).trim());
//			client = ClientBuilder.newClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	public synchronized static WebTarget getTargetAllPosts() {
		UriBuilder uriBui = UriBuilder
				.fromPath("http://"+host+":"+port)
				.path("ownjodel")
				.path("rs")
				.path("posts");
		return ClientBuilder.newClient().target(uriBui);
	}
	public synchronized static WebTarget getTargetPostsByLoc(){
		UriBuilder uriBui = UriBuilder
				.fromPath("http://"+host+":"+port)
				.path("ownjodel")
				.path("rs")
				.path("loc")
				.path("{lat}")
				.path("{lon}");
		return ClientBuilder.newClient().target(uriBui);
	}
	public synchronized static WebTarget getTargetSinglePost(){
		UriBuilder uriBui = UriBuilder
				.fromPath("http://"+host+":"+port)
				.path("ownjodel")
				.path("rs")
				.path("posts")
				.path("{id}");
		return ClientBuilder.newClient().target(uriBui);
	}
	public synchronized static void releaseClient(){
//		client.close();
//		client = ClientBuilder.newClient();
	}
	
}
