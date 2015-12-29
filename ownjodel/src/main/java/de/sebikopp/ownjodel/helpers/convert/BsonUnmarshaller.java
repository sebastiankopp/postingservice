package de.sebikopp.ownjodel.helpers.convert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import de.sebikopp.ownjodel.helpers.ConstantValues;
import de.sebikopp.ownjodel.model.GeoLocSpot;
import de.sebikopp.ownjodel.model.GeoPosition;
import de.sebikopp.ownjodel.model.Post;
import de.sebikopp.ownjodel.model.Vote;

public class BsonUnmarshaller {
	@SuppressWarnings("unchecked")
	public static Post bsonToPost(Document src){
		Post rc = new Post();
		List<Vote> votes;
		List<Post> subposts;
		try {
			List<Document> _votes = (List<Document>) src.get(ConstantValues.JBSON_KEY_POST_VOTES);
			votes = new ArrayList<>(_votes.size());
			_votes.stream().map(BsonUnmarshaller::bsonToVote).forEach(votes::add);
			rc.setVotes(votes);
		} catch (Exception e) {
			votes = new ArrayList<>(1);
		}
		
		try {
			List<Document> _subposts = (List<Document>) src.get(ConstantValues.JBSON_KEY_POST_SUBPOSTS);
			subposts = new ArrayList<>(_subposts.size());
			_subposts.stream().map(BsonUnmarshaller::bsonToPost).forEach(subposts::add);
			rc.setSubposts(subposts);
		} catch (Exception e) {
			subposts = new ArrayList<>(1);
		}
		
		rc.setContent(StringConverter.escapeHtml(src.getString(ConstantValues.JBSON_KEY_POST_CONTENT)));
		rc.setTitle(StringConverter.escapeHtml(src.getString(ConstantValues.JBSON_KEY_POST_TITLE)));
		rc.setId(StringConverter.escapeHtml(src.getString(ConstantValues.JBSON_KEY_POST_ID)));
		
		rc.setSubposts(subposts);
		rc.setVotes(votes);
		
		Document _pos = (Document) src.get(ConstantValues.JBSON_KEY_POST_GEOPOS);
		rc.setPos(BsonUnmarshaller.bsonToGeopos(_pos));
		
		String ldtStr = src.getString(ConstantValues.JBSON_KEY_POST_DATETIME);
		LocalDateTime ldt = null;
		try {
			ldt = LocalDateTime.parse(ldtStr, DateTimeFormatter.ofPattern(ConstantValues.DATE_TIME_PATTERN));
		} catch (Exception e) {
			System.err.println(ldtStr + " konnte nicht nach dem Schema " + ConstantValues.DATE_TIME_PATTERN + " geparst werden.");
		}
		rc.setLdt(ldt);
		
		return rc;
	}
	public static Vote bsonToVote(Document src){
		Vote rc = new Vote();
		String jsessid = StringConverter.escapeHtml(src.getString(ConstantValues.JBSON_KEY_VOTE_SESSIONID));
		Boolean upvote = src.getBoolean(ConstantValues.JBSON_KEY_VOTE_BOOL_UPVOTE);
		if (jsessid == null || upvote == null)
			throw new NullPointerException("Mind. 1 Parameter einer Vote-Instanz nicht instantiiert.");
		rc.setJsessionid(jsessid);
		rc.setUp(upvote);
		return rc;
	}
	public static GeoPosition bsonToGeopos(Document src){
		if (!src.getString(ConstantValues.BSON_GEO_KEY_LOCTYPE).equals(ConstantValues.BSON_LOCTYPE_POINT)){
			throw new IllegalArgumentException("Any other location types than points are currently not supported!");
		}
		@SuppressWarnings("unchecked")
		List<Double> coords = (List<Double>) src.get(ConstantValues.BSON_GEO_KEY_COORDINATES);
		GeoPosition rc = new GeoPosition();
		rc.setLatitude(coords.get(1));
		rc.setLongitude(coords.get(0));
		return rc;
	}
	public static GeoLocSpot bsonToGeoLocSpot(Document src){
		Document _gpos = (Document) src.get(ConstantValues.JBSON_KEY_GEO_LOC_SPOT_GEOPOS);
		GeoPosition gpos = BsonUnmarshaller.bsonToGeopos(_gpos);
		String glsid = StringConverter.escapeHtml(src.getString(ConstantValues.JBSON_KEY_GEO_LOC_SPOT_ID));
		String name = StringConverter.escapeHtml(src.getString(ConstantValues.JBSON_KEY_GEO_LOC_SPOT_NAME));
		GeoLocSpot rc = new GeoLocSpot();
		rc.setId(glsid);
		rc.setName(name);
		rc.setPos(gpos);
		return rc;
	}
}
