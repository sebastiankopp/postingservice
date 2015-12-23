package de.sebikopp.ownjodel.helpers;

public interface ConstantValues {
	public static final String MONGO_DB_NAME = "ownjodeldb";
	public static final String POSTS_COLLECTION = "postscollection";
	public static final String LOCATIONS_COLLECTION = "locscollection";
	
	public static final String JBSON_KEY_POST_ID = "postid"; // nicht die technische ID
	public static final String JBSON_KEY_POST_TITLE = "title";
	public static final String JBSON_KEY_POST_CONTENT = "content";
	public static final String JBSON_KEY_POST_SUBPOSTS = "subposts";
	public static final String JBSON_KEY_POST_DATETIME = "datetime";
	public static final String JBSON_KEY_POST_GEOPOS = "pos";
	public static final String JBSON_KEY_POST_VOTES = "votes";
	
	public static final String JBSON_KEY_GEOPOS_LATITUDE = "latitude";
	public static final String JBSON_KEY_GEOPOS_LONGITUDE = "longitude";
	
	public static final String JBSON_KEY_GEO_LOC_SPOT_NAME = "name"; 
	public static final String JBSON_KEY_GEO_LOC_SPOT_ID = "locid"; // nicht die technische id
	public static final String JBSON_KEY_GEO_LOC_SPOT_GEOPOS = "pos";
	
	public static final String JBSON_KEY_VOTE_BOOL_UPVOTE = "upvote";
	public static final String JBSON_KEY_VOTE_SESSIONID = "jsessionid";
	
	public static final String DATE_TIME_PATTERN = "YYYY-MM-dd HH:mm:ss";
	
}