package de.sebikopp.ownjodel.helpers;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.mongodb.MongoClient;

public class Resources {
	@Inject
	private InitLoader initload;
	@Produces
	@Default
	public MongoClient getMongoClient() {
		return new MongoClient(initload.getMongoHost(), initload.getMongoPort());
	}
}
