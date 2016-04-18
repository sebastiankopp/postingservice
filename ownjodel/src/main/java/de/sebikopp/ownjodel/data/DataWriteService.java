package de.sebikopp.ownjodel.data;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

import de.sebikopp.ownjodel.helpers.ConstantValues;
import de.sebikopp.ownjodel.helpers.convert.BsonMarshaller;
import de.sebikopp.ownjodel.helpers.intercept.Stopwatch;
import de.sebikopp.ownjodel.model.GeoLocSpot;
import de.sebikopp.ownjodel.model.Post;
import de.sebikopp.ownjodel.model.Vote;

/**
 * Service providing create and update operations on the database. Delete will be supported later...
 * @author Sebastian
 *
 */
@Stateless
@Interceptors(Stopwatch.class)
public class DataWriteService {
	@Inject
	private MongoClient mc;
	private MongoCollection<Document> collPosts;
	private MongoCollection<Document> collLocs;
	@Inject
	private DataReadService read;
	@PostConstruct
	public void init() {
//		mc = new MongoClient();
		collPosts = mc.getDatabase(ConstantValues.MONGO_DB_NAME).getCollection(ConstantValues.POSTS_COLLECTION);
		collLocs = mc.getDatabase(ConstantValues.MONGO_DB_NAME).getCollection(ConstantValues.LOCATIONS_COLLECTION);
	}

	@PreDestroy
	public void predestroy() {
		mc.close();
	}
	public void persistNewPost(Post post) {
		Document toIns = BsonMarshaller.postToBson(post);
		collPosts.insertOne(toIns);
	}
	public void updatePost(Post refreshed) {
		Document searchObj = new Document().append(ConstantValues.JBSON_KEY_POST_ID, refreshed.getId());
		collPosts.replaceOne(searchObj, BsonMarshaller.postToBson(refreshed));
	}
	public void persistNewLocation(GeoLocSpot gls) {
		Document toIns = BsonMarshaller.geoLocSpotToBson(gls);
		collLocs.insertOne(toIns);
	}
	public Post votePost(String postId, boolean up, String sessionData) {
		Vote vote = new Vote();
		vote.setJsessionid(sessionData); 
		vote.setUp(up);
		Post obj = read.getPostById(postId);
		obj.getVotes().add(vote);
		Document upd = BsonMarshaller.postToBson(obj);
		collPosts.findOneAndReplace(new Document(ConstantValues.JBSON_KEY_POST_ID, postId), upd);
		return obj;
	}
}
