package de.sebikopp.ownjodel.helpers;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;

public class Resources {
	@Produces
	@Default
	public MongoClient getMongoClient() {
		return new MongoClient();
	}

	@Produces
	@Default
	public Gson getGson() {
		return new GsonBuilder()
				.setDateFormat(ConstantValues.DATE_TIME_PATTERN)
				.serializeNulls()
				.setPrettyPrinting()
				.create();
	}
}
