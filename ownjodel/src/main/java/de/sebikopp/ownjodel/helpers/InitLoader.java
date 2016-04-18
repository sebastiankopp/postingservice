package de.sebikopp.ownjodel.helpers;

import javax.ejb.Startup;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class InitLoader {
	private String mongoHost;
	private int mongoPort;
	@PostConstruct
	public void init(){
		ClassLoader cl = InitLoader.class.getClassLoader();
		Properties props = new Properties();
		InputStream instr = cl.getResourceAsStream(ConstantValues.PATH_TO_MONGO_PROPS);
		try {
			props.load(instr);
			this.mongoHost = props.getProperty(ConstantValues.MONGO_PROP_KEY_HOST);
			this.mongoPort = Integer.parseInt(props.getProperty(ConstantValues.MONGO_PROP_KEY_PORT));
		} catch (IOException e) {
			System.err.println("Fatal error occured while loading properties");
			e.printStackTrace();
			try {
				mongoHost = Inet4Address.getLocalHost().getHostAddress();
				mongoPort = ConstantValues.MONGO_DEFAUT_PORT;
			} catch (UnknownHostException e1) {
				System.err.println("Very fatal error");
				e1.printStackTrace();
			}
		}
	}
	public String getMongoHost() {
		return mongoHost;
	}

	public int getMongoPort() {
		return mongoPort;
	}
}
