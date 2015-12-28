package de.sebikopp.ownjodel.helpers;

public interface ConstantValues {
	public static final String MONGO_DB_NAME = "ownjodeldb";
	public static final String POSTS_COLLECTION = "postscollection";
	public static final String LOCATIONS_COLLECTION = "locscollection";
	
	public static final String JBSON_KEY_POST_ID = "postid"; // self-created, non-technical ID
	public static final String JBSON_KEY_POST_TITLE = "title";
	public static final String JBSON_KEY_POST_CONTENT = "content";
	public static final String JBSON_KEY_POST_SUBPOSTS = "subposts";
	public static final String JBSON_KEY_POST_DATETIME = "datetime";
	public static final String JBSON_KEY_POST_GEOPOS = "pos";
	public static final String JBSON_KEY_POST_VOTES = "votes";
	
	public static final String JBSON_KEY_GEOPOS_LATITUDE = "latitude";
	public static final String JBSON_KEY_GEOPOS_LONGITUDE = "longitude";
	
	public static final String JBSON_KEY_GEO_LOC_SPOT_NAME = "name"; 
	public static final String JBSON_KEY_GEO_LOC_SPOT_ID = "locid"; // self-created, non-technical ID
	public static final String JBSON_KEY_GEO_LOC_SPOT_GEOPOS = "pos";
	
	public static final String JBSON_KEY_VOTE_BOOL_UPVOTE = "upvote";
	public static final String JBSON_KEY_VOTE_SESSIONID = "jsessionid";
	
	public static final String DATE_TIME_PATTERN = "YYYY-MM-dd HH:mm:ss";
	
	public static final String BSON_KEY_LOCATION = "location";
	public static final String BSON_GEO_KEY_LOCTYPE = "type";
	public static final String BSON_GEO_KEY_COORDINATES = "coordinates";
	
	public static final String BSON_LOCTYPE_POINT = "Point";
	public static final String BSON_LOCTYPE_POLYGON = "Polygon";
	
	public static final String BSON_$GEOMETRY = "$geometry";
	public static final String BSON_$MAXDIST ="$maxDistance";
	public static final String BSON_$NEARSPHERE = "$nearSphere";
	
	public static final int MAX_DIST_NEAR_IN_METRES = 15000;
}
