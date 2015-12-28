package de.sebikopp.ownjodel.rs;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.sebikopp.ownjodel.data.DataReadService;
import de.sebikopp.ownjodel.data.DataWriteService;
import de.sebikopp.ownjodel.helpers.ConstantValues;
import de.sebikopp.ownjodel.helpers.IdGenerator;
import de.sebikopp.ownjodel.helpers.intercept.ExceptionHandlerRest;
import de.sebikopp.ownjodel.model.GeoLocSpot;
import de.sebikopp.ownjodel.model.GeoPosition;
import de.sebikopp.ownjodel.model.Post;

@Stateless
@Path("loc")
public class LocationService {
	@Inject
	private DataWriteService write;
	@Inject
	private DataReadService read;
	@Inject
	private Gson gson;
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("")
	@Interceptors(ExceptionHandlerRest.class)
	public String addLocationSpot(String json){
		Type type = new TypeToken<Map<String, Object>>(){}.getType();
		Map<String,Object> map = gson.fromJson(json, type);
		Double lat = (Double) map.get(ConstantValues.JBSON_KEY_GEOPOS_LATITUDE);
		Double lon = (Double) map.get(ConstantValues.JBSON_KEY_GEOPOS_LONGITUDE);
		String loc = (String) map.get(ConstantValues.JBSON_KEY_GEO_LOC_SPOT_NAME);
		
		GeoLocSpot gls = new GeoLocSpot();
		GeoPosition pos = new GeoPosition();
		pos.setLatitude(lat);
		pos.setLongitude(lon);
		
		gls.setPos(pos);
		gls.setName(loc);
		String id = IdGenerator.newLocId();
		gls.setId(id);
		write.persistNewLocation(gls);
		return  gson.toJson(gls, GeoLocSpot.class);
	}
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllGeoLocSpots(){
		List<GeoLocSpot> rc = read.getAllNamedLocations();
		Type type = new TypeToken<List<GeoLocSpot>>(){}.getType();
		return gson.toJson(rc, type);
	}
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getGeoLocSpotById(@PathParam("id") String id){
		GeoLocSpot gls = read.getGeoLocSpotById(id);
		return gson.toJson(gls, GeoLocSpot.class);
	}
	@GET
	@Path("/near/{lat}/{lon}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPostsNearYou(@PathParam("lat")String lat, @PathParam("lon") String lon){
		// core query behind is still inefficient
		GeoPosition queryPos = new GeoPosition();
		queryPos.setLatitude(Double.parseDouble(lat));
		queryPos.setLongitude(Double.parseDouble(lon));
		List<Post> posts = read.readNearPosts(queryPos);
		Type type = new TypeToken<List<Post>>(){}.getType();
		return gson.toJson(posts, type);
	}
	@GET
	@Path("/nearposts/{locname}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPostsByLocName(@PathParam("locname")String locName){
		List<Post> rc = read.readPostsByNamedLocation(locName);
		Type type = new TypeToken<List<Post>>(){}.getType();
		return gson.toJson(rc, type);
	}
	// TODO maybe further operations are needed

}
