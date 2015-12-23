package de.sebikopp.ownjodel.rs;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.sebikopp.ownjodel.data.DataReadService;
import de.sebikopp.ownjodel.data.DataWriteService;
import de.sebikopp.ownjodel.helpers.ConstantValues;
import de.sebikopp.ownjodel.helpers.IdGenerator;
import de.sebikopp.ownjodel.helpers.intercept.ExceptionHandlerRest;
import de.sebikopp.ownjodel.model.GeoPosition;
import de.sebikopp.ownjodel.model.Post;
import de.sebikopp.ownjodel.model.Vote;

@Stateless
@Path("posts")
public class PostingService {
	@Inject
	private DataReadService read;
	@Inject
	private DataWriteService write;
	@Inject
	private Gson gson;
	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String publishNewPost(String json){
		Type type = new TypeToken<Map<String, Object>>(){}.getType();
		Map<String,Object> map = gson.fromJson(json, type);
		Double lat = (Double) map.get(ConstantValues.JBSON_KEY_GEOPOS_LATITUDE);
		Double lon = (Double) map.get(ConstantValues.JBSON_KEY_GEOPOS_LONGITUDE);
		String title = (String) map.get(ConstantValues.JBSON_KEY_POST_TITLE);
		String content = (String) map.get(ConstantValues.JBSON_KEY_POST_CONTENT);
		
		Post rc = new Post();
		GeoPosition pos  = new GeoPosition();
		pos.setLatitude(lat);
		pos.setLongitude(lon);
		rc.setPos(pos);
		
		rc.setContent(content);
		rc.setTitle(title);
		rc.setLdt(LocalDateTime.now());
		rc.setSubposts(new ArrayList<>());
		rc.setVotes(new ArrayList<>());
		
		rc.setId(IdGenerator.newPostId());
		write.persistNewPost(rc);
		
		return gson.toJson(rc, Post.class);
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{father}")
	@Interceptors(ExceptionHandlerRest.class)
	public String createSubpost(String json, @PathParam("father") String fatherId){
		Type type = new TypeToken<Map<String, Object>>(){}.getType();
		Map<String,Object> map = gson.fromJson(json, type);
		Double lat = (Double) map.get(ConstantValues.JBSON_KEY_GEOPOS_LATITUDE);
		Double lon = (Double) map.get(ConstantValues.JBSON_KEY_GEOPOS_LONGITUDE);
		String title = (String) map.get(ConstantValues.JBSON_KEY_POST_TITLE);
		String content = (String) map.get(ConstantValues.JBSON_KEY_POST_CONTENT);
		
		Post rc = new Post();
		GeoPosition pos  = new GeoPosition();
		pos.setLatitude(lat);
		pos.setLongitude(lon);
		rc.setPos(pos);
		
		rc.setContent(content);
		rc.setTitle(title);
		rc.setLdt(LocalDateTime.now());
		rc.setSubposts(new ArrayList<>());
		rc.setVotes(new ArrayList<>());
		rc.setId(IdGenerator.newPostId());
		
		Post father = read.getPostById(fatherId);
		father.getSubposts().add(rc);
		write.updatePost(father); // entscheidend
		return gson.toJson(father, Post.class);
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{postid}")
	@Interceptors(ExceptionHandlerRest.class)
	public String votePost(@PathParam("postid") String postid, String json, @Context HttpServletRequest req){
		String sessid = req.getSession(true).getId();
		Type type = new TypeToken<Map<String, Object>>(){}.getType();
		Map<String,Object> map = gson.fromJson(json, type);
		Boolean up = (Boolean) map.get(ConstantValues.JBSON_KEY_VOTE_BOOL_UPVOTE);
		
		Post father = read.getPostById(postid);
		Vote v = new Vote();
		v.setJsessionid(sessid);
		v.setUp(up);
		father.getVotes().add(v);
		write.updatePost(father);
		return gson.toJson(father, Post.class);
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("")
	public String seeAllPosts(){
		List<Post> rc = read.getAllPosts();
		Type type = new TypeToken<List<Post>>(){}.getType();
		return gson.toJson(rc, type);
	}
	@GET
	@Path("{postid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Interceptors(ExceptionHandlerRest.class)
	public String seePostById(@PathParam("postid") String postid){
		Post preRc = read.getPostById(postid);
		return gson.toJson(preRc, Post.class);
	}
}
