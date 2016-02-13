package de.sebikopp.ownjodel.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import de.sebikopp.ownjodel.helpers.ConstantValues;
import de.sebikopp.ownjodel.helpers.convert.BsonUnmarshaller;
import de.sebikopp.ownjodel.helpers.intercept.Stopwatch;
import de.sebikopp.ownjodel.model.GeoLocSpot;
import de.sebikopp.ownjodel.model.GeoPosition;
import de.sebikopp.ownjodel.model.Post;

/**
 * Service performing read-only operations on the MongoDB back-end of this application.
 * @author Sebastian
 *
 */
@Stateless
@Interceptors(Stopwatch.class)
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
	/**
	 * Returns posts within a circle of 15000 metres using the geospatial API of MongoDB. (shall i use the german, anglo-american or swiss notation? ;))
	 * @param pos centre of the specifed circle
	 * @return see above
	 */
	public List<Post> readNearPosts(GeoPosition pos){
		// see also https://docs.mongodb.org/v3.0/tutorial/geospatial-tutorial/
		List<Double> ptCoords = Arrays.asList(pos.getLongitude(), pos.getLatitude());
		Document ptDoc = new Document(ConstantValues.BSON_GEO_KEY_LOCTYPE, ConstantValues.BSON_LOCTYPE_POINT);
		ptDoc.put(ConstantValues.BSON_GEO_KEY_COORDINATES, ptCoords);
		
		Document nearSphereDoc = new Document(ConstantValues.BSON_$GEOMETRY, ptDoc);
		nearSphereDoc.put(ConstantValues.BSON_$MAXDIST, ConstantValues.MAX_DIST_NEAR_IN_METRES);
		
		Document geoOpQ = new Document(ConstantValues.BSON_$NEARSPHERE, nearSphereDoc);
		
		Document query = new Document(ConstantValues.JBSON_KEY_POST_GEOPOS, geoOpQ);
		FindIterable<Document> lst = collPosts.find(query);
		List<Post> rc = new ArrayList<>();
		for (Document doc: lst){
			rc.add(BsonUnmarshaller.bsonToPost(doc));
		}
		return rc;
	}
	public List<Post> readPostsByNamedLocation(String locname){	
		List<GeoLocSpot> spots = getLocationsByName(locname);
		Set<Post> rc = new HashSet<>();
		for (GeoLocSpot dd: spots){
			rc.addAll(readNearPosts(dd.getPos()));
		}
		return new ArrayList<>(rc);
	}
	public List<GeoLocSpot> getAllNamedLocations(){
		List<GeoLocSpot> rc = new ArrayList<>();
		for (Document dd: collLocs.find()){
			rc.add(BsonUnmarshaller.bsonToGeoLocSpot(dd));
		}
		return rc;
	}
	public GeoLocSpot getGeoLocSpotById(String id) {
		Document query = new Document(ConstantValues.JBSON_KEY_GEO_LOC_SPOT_ID, id);
		FindIterable<Document> lst = collLocs.find(query);
		GeoLocSpot rc = null;
		rc = BsonUnmarshaller.bsonToGeoLocSpot(lst.first());
		return rc;
	}
	public Post getPostById(String postId) {
		Document query = new Document(ConstantValues.JBSON_KEY_POST_ID, postId);
		System.out.println("Requesting documents that match " + query);
		System.out.println("Total size postscollection: "+ collPosts.count());
		FindIterable<Document> lst = collPosts.find(query);
		Post rc = null;
		try {
			rc = BsonUnmarshaller.bsonToPost(lst.first());
		} catch (NullPointerException npe) {
			npe.printStackTrace(System.out);
		}
		return rc;
	}
	public List<GeoLocSpot> getLocationsByName(String name){
		Document query = new Document().append(ConstantValues.JBSON_KEY_GEO_LOC_SPOT_NAME, name);
		FindIterable<Document> lst = collLocs.find(query);
		List<GeoLocSpot> rc = new ArrayList<>();
		for (Document doc: lst){
			rc.add(BsonUnmarshaller.bsonToGeoLocSpot(doc));
		}
		return rc;
	}
		// only intended for prototype phase
	public List<Post> getAllPosts(){
		List<Post> rc = new ArrayList<>();
		for (Document dd: collPosts.find()){
			rc.add(BsonUnmarshaller.bsonToPost(dd));
		}
		return rc;
	}
}
