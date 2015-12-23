package de.sebikopp.ownjodel.data;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

import de.sebikopp.ownjodel.helpers.ConstantValues;
import de.sebikopp.ownjodel.helpers.convert.BsonMarshaller;
import de.sebikopp.ownjodel.helpers.intercept.Stopwatch;
import de.sebikopp.ownjodel.model.GeoLocSpot;
import de.sebikopp.ownjodel.model.Post;

@Stateless
public class DataWriteService {
	private MongoClient mc;
	private MongoCollection<Document> collPosts;
	private MongoCollection<Document> collLocs;

	@PostConstruct
	public void init() {
		mc = new MongoClient();
		collPosts = mc.getDatabase(ConstantValues.MONGO_DB_NAME).getCollection(ConstantValues.POSTS_COLLECTION);
		collLocs = mc.getDatabase(ConstantValues.MONGO_DB_NAME).getCollection(ConstantValues.LOCATIONS_COLLECTION);
	}

	@PreDestroy
	public void predestroy() {
		mc.close();
	}
	@Interceptors(Stopwatch.class)
	public void persistNewPost(Post post) {
		Document toIns = BsonMarshaller.postToBson(post);

//		 Document postIdFindQuery = new
//		 Document(ConstantValues.JBSON_KEY_POST_ID, post.getId());
//		 long count = collPosts.count(postIdFindQuery);
//		 if (count > 0)
//		 throw new IllegalArgumentException("Id existiert bereits");

		collPosts.insertOne(toIns);
	}
	@Interceptors(Stopwatch.class)
	public void updatePost(Post refreshed) {
		Document searchObj = new Document().append(ConstantValues.JBSON_KEY_POST_ID, refreshed.getId());
		collPosts.replaceOne(searchObj, BsonMarshaller.postToBson(refreshed));
	}
	@Interceptors(Stopwatch.class)
	public void persistNewLocation(GeoLocSpot gls) {
		Document toIns = BsonMarshaller.geoLocSpotToBson(gls);

		collLocs.insertOne(toIns);
	}
}
