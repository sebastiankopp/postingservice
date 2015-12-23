package de.sebikopp.ownjodel.data;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import de.sebikopp.ownjodel.helpers.ConstantValues;
import de.sebikopp.ownjodel.helpers.GeoLocDistanceCalculator;
import de.sebikopp.ownjodel.helpers.convert.BsonUnmarshaller;
import de.sebikopp.ownjodel.helpers.intercept.Stopwatch;
import de.sebikopp.ownjodel.model.GeoLocSpot;
import de.sebikopp.ownjodel.model.GeoPosition;
import de.sebikopp.ownjodel.model.Post;

@Stateless
public class DataReadService {
	private MongoClient mc;
	private MongoCollection<Document> collLocs;
	private MongoCollection<Document> collPosts;
	@PostConstruct
	public void init(){
		mc = new MongoClient();
		collLocs = mc.getDatabase(ConstantValues.MONGO_DB_NAME).getCollection(ConstantValues.LOCATIONS_COLLECTION);
		collPosts = mc.getDatabase(ConstantValues.MONGO_DB_NAME).getCollection(ConstantValues.POSTS_COLLECTION);
	}
	@PreDestroy
	public void preDestroy(){
		mc.close();
	}
	@Interceptors(Stopwatch.class)
	public List<Post> readNearPosts(GeoPosition pos){
		List<Post> rc = new ArrayList<>();
		for (Document doc: collPosts.find()){
			Post p = BsonUnmarshaller.bsonToPost(doc);
			if (GeoLocDistanceCalculator.isNearLoc(p.getPos(), pos)){
				rc.add(p);
			}
		}
		return rc;
	}
	@Interceptors(Stopwatch.class)
	public List<Post> readPostsByNamedLocation(String locname){
		List<GeoLocSpot> spots = getLocationsByName(locname);
		List<Post> rc = new ArrayList<>();
		for (GeoLocSpot dd: spots){
			rc.addAll(readNearPosts(dd.getPos()));
		}
		return rc;
	}
	@Interceptors(Stopwatch.class)
	public List<GeoLocSpot> getAllNamedLocations(){
		List<GeoLocSpot> rc = new ArrayList<>();
		for (Document dd: collLocs.find()){
			rc.add(BsonUnmarshaller.bsonToGeoLocSpot(dd));
		}
		return rc;
	}
	@Interceptors(Stopwatch.class)
	public GeoLocSpot getGeoLocSpotById(String id) {
		Document query = new Document(ConstantValues.JBSON_KEY_GEO_LOC_SPOT_ID, id);
		FindIterable<Document> lst = collLocs.find(query);
		GeoLocSpot rc = null;
		rc = BsonUnmarshaller.bsonToGeoLocSpot(lst.first());
		return rc;
	}
	@Interceptors(Stopwatch.class)
	public Post getPostById(String fatherId) {
		Document query = new Document(ConstantValues.JBSON_KEY_POST_ID, fatherId);
		FindIterable<Document> lst = collLocs.find(query);
		Post rc = null;
		try {
			rc = BsonUnmarshaller.bsonToPost(lst.first());
		} catch (NullPointerException npe) {
			npe.printStackTrace(System.out);
		}
		return rc;
	}
	@Interceptors(Stopwatch.class)
	public List<GeoLocSpot> getLocationsByName(String name){
		Document query = new Document().append(ConstantValues.JBSON_KEY_GEO_LOC_SPOT_NAME, name);
		FindIterable<Document> lst = collLocs.find(query);
		List<GeoLocSpot> rc = new ArrayList<>();
		for (Document doc: lst){
			rc.add(BsonUnmarshaller.bsonToGeoLocSpot(doc));
		}
		return rc;
	}
	@Interceptors(Stopwatch.class)	// fürs Prototypenstadium
	public List<Post> getAllPosts(){
		List<Post> rc = new ArrayList<>();
		for (Document dd: collPosts.find()){
			rc.add(BsonUnmarshaller.bsonToPost(dd));
		}
		return rc;
	}
}
