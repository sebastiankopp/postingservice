package de.sebikopp.ownjodel.helpers;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

import com.mongodb.MongoClient;

public class Resources {
	@Produces
	@Default
	public MongoClient getMongoClient() {
		return new MongoClient();
	}

}
