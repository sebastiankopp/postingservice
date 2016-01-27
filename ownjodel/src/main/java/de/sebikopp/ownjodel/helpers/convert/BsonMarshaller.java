package de.sebikopp.ownjodel.helpers.convert;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import de.sebikopp.ownjodel.helpers.ConstantValues;
import de.sebikopp.ownjodel.model.GeoLocSpot;
import de.sebikopp.ownjodel.model.GeoPosition;
import de.sebikopp.ownjodel.model.Post;
import de.sebikopp.ownjodel.model.Vote;

public class BsonMarshaller {
	public static Document postToBson(Post p){
		List<Document> subposts = new ArrayList<>();
		for (Post subpost: p.getSubposts()){
			subposts.add(postToBson(subpost));
		}
		List<Document> votes = new ArrayList<>();
		for (Vote vote: p.getVotes()){
			votes.add(convertVoteToBson(vote));
		}
		Document doc = new Document(ConstantValues.JBSON_KEY_POST_ID, p.getId())
				.append(ConstantValues.JBSON_KEY_POST_TITLE, p.getTitle())
				.append(ConstantValues.JBSON_KEY_POST_CONTENT, p.getContent())
				.append(ConstantValues.JBSON_KEY_POST_SUBPOSTS, subposts)
				.append(ConstantValues.JBSON_KEY_POST_DATETIME, p.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME))
				.append(ConstantValues.JBSON_KEY_POST_GEOPOS, convertGeoposToBson(p.getPos()))
				.append(ConstantValues.JBSON_KEY_POST_VOTES, votes);
		return doc;
	}
	// GeoJSON specification: [longitude (x-value), latitude (y-val)]
	public static Document convertGeoposToBson(GeoPosition pos){
		Document doc = new Document(ConstantValues.BSON_GEO_KEY_LOCTYPE, ConstantValues.BSON_LOCTYPE_POINT)
				.append(ConstantValues.BSON_GEO_KEY_COORDINATES, Arrays.asList(pos.getLongitude(), pos.getLatitude()));
		return doc;
	}
	
	public static Document convertVoteToBson(Vote v){
		Document doc = new Document(ConstantValues.JBSON_KEY_VOTE_SESSIONID, v.getJsessionid())
				.append(ConstantValues.JBSON_KEY_VOTE_BOOL_UPVOTE, v.isUp());
		return doc;
	}
	
	public static Document geoLocSpotToBson(GeoLocSpot gls){
		Document doc = new Document(ConstantValues.JBSON_KEY_GEO_LOC_SPOT_ID, gls.getId())
				.append(ConstantValues.JBSON_KEY_GEO_LOC_SPOT_NAME, gls.getName())
				.append(ConstantValues.JBSON_KEY_GEO_LOC_SPOT_GEOPOS, convertGeoposToBson(gls.getPos()));
		return doc;
	}
}
