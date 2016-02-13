package de.sebikopp.ownjodel.boundary;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import de.sebikopp.ownjodel.data.DataReadService;
import de.sebikopp.ownjodel.data.DataWriteService;
import de.sebikopp.ownjodel.helpers.ConstantValues;
import de.sebikopp.ownjodel.helpers.IdGenerator;
import de.sebikopp.ownjodel.helpers.intercept.ExceptionHandlerRest;
import de.sebikopp.ownjodel.model.GeoPosition;
import de.sebikopp.ownjodel.model.Post;

@Stateless
@Path("posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Interceptors(ExceptionHandlerRest.class)
public class PostingService {
	@Inject
	private DataReadService read;
	@Inject
	private DataWriteService write;
	
	@POST
	@Path("")
	public Response publishNewPost(JsonObject submittedPost, @Context HttpServletRequest req){
		System.out.println("Post published from " + req.getRemoteHost() + " at Session " + req.getSession(true).getId());
		JsonNumber lat =  submittedPost.getJsonNumber(ConstantValues.JBSON_KEY_GEOPOS_LATITUDE);
		JsonNumber lon = submittedPost.getJsonNumber(ConstantValues.JBSON_KEY_GEOPOS_LONGITUDE);
		String title = submittedPost.getString(ConstantValues.JBSON_KEY_POST_TITLE);
		String content = submittedPost.getString(ConstantValues.JBSON_KEY_POST_CONTENT);
		
		Post rc = new Post();
		GeoPosition pos  = new GeoPosition();
		pos.setLatitude(lat.doubleValue());
		pos.setLongitude(lon.doubleValue());
		rc.setPos(pos);
		
		rc.setContent(content);
		rc.setTitle(title);
		rc.setDateTime(LocalDateTime.now());
		rc.setSubposts(new ArrayList<>());
		rc.setVotes(new ArrayList<>());
		
		rc.setId(IdGenerator.newPostId());
		write.persistNewPost(rc);
		
		return Response.ok(rc).build();
	}
	@POST
	@Path("{father}")
	public Response createSubpost(String json, @PathParam("father") String fatherId, @Context HttpServletRequest req){
		req.getSession(true);
		JsonReader reader = Json.createReader(new StringReader(json));
		JsonObject obj = reader.readObject();
		
		JsonNumber lat =  obj.getJsonNumber(ConstantValues.JBSON_KEY_GEOPOS_LATITUDE);
		JsonNumber lon = obj.getJsonNumber(ConstantValues.JBSON_KEY_GEOPOS_LONGITUDE);
		String title = obj.getString(ConstantValues.JBSON_KEY_POST_TITLE);
		String content = obj.getString(ConstantValues.JBSON_KEY_POST_CONTENT);
		
		Post rc = new Post();
		GeoPosition pos  = new GeoPosition();
		pos.setLatitude(lat.doubleValue());
		pos.setLongitude(lon.doubleValue());
		rc.setPos(pos);
		
		rc.setContent(content);
		rc.setTitle(title);
		rc.setDateTime(LocalDateTime.now());
		rc.setSubposts(new ArrayList<>());
		rc.setVotes(new ArrayList<>());
		rc.setId(IdGenerator.newPostId());
		
		Post father = read.getPostById(fatherId);
		father.getSubposts().add(rc);
		write.updatePost(father); // entscheidend
		return Response.ok(father).build();
	}
	/**
	 * 
	 * @param postid id of voted post
	 * @param postVote object, declaring if user likes voted post: <pre>{"up":true}</pre> bzw. <pre>{"up":false}</pre>
	 * @param req HttpServletRequest for retrieving session data
	 * @return voted post
	 */
	@PUT
	@Path("{postid}")
	public Response votePost(@PathParam("postid") String postid, JsonObject postVote, @Context HttpServletRequest req){
		String sessid = req.getSession(true).getId();
		JsonValue val = postVote.get(ConstantValues.JBSON_KEY_VOTE_BOOL_UPVOTE);
		boolean up;
		if (val.getValueType().equals(ValueType.TRUE)){
			up = true;
		} else if (val.getValueType().equals(ValueType.FALSE)){
			up = false;
		} else {
			return Response.status(Status.BAD_REQUEST.getStatusCode()).build();
		}
		Post father = write.votePost(postid, up, sessid);
		return Response.ok(father).build();
	}
	@GET
	@Path("")
	public Response seeAllPosts(@Context HttpServletRequest req){
		req.getSession(true);
		return Response.ok(read.getAllPosts()).build();
	}
	@GET
	@Path("{postid}")
	public Response seePostById(@PathParam("postid") String postid, @Context HttpServletRequest req){
		req.getSession(true);
		return Response.ok(read.getPostById(postid)).build();
	}
}
