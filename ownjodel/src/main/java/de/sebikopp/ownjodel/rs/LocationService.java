package de.sebikopp.ownjodel.rs;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.sebikopp.ownjodel.data.DataReadService;
import de.sebikopp.ownjodel.data.DataWriteService;
import de.sebikopp.ownjodel.helpers.ConstantValues;
import de.sebikopp.ownjodel.helpers.IdGenerator;
import de.sebikopp.ownjodel.helpers.intercept.ExceptionHandlerRest;
import de.sebikopp.ownjodel.helpers.intercept.Stopwatch;
import de.sebikopp.ownjodel.model.GeoLocSpot;
import de.sebikopp.ownjodel.model.GeoPosition;

@Stateless
@Path("loc")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Interceptors({ExceptionHandlerRest.class, Stopwatch.class})
public class LocationService {
	@Inject
	private DataWriteService write;
	@Inject
	private DataReadService read;

	@POST
	@Path("")
	public Response addLocationSpot(JsonObject objReq){
		JsonNumber lat = objReq.getJsonNumber(ConstantValues.JBSON_KEY_GEOPOS_LATITUDE);
		JsonNumber lon = objReq.getJsonNumber(ConstantValues.JBSON_KEY_GEOPOS_LONGITUDE);
		String loc = objReq.getString(ConstantValues.JBSON_KEY_GEO_LOC_SPOT_NAME);
		
		GeoLocSpot gls = new GeoLocSpot();
		GeoPosition pos = new GeoPosition();
		pos.setLatitude(lat.doubleValue());
		pos.setLongitude(lon.doubleValue());
		
		gls.setPos(pos);
		gls.setName(loc);
		String id = IdGenerator.newLocId();
		gls.setId(id);
		write.persistNewLocation(gls);
		return Response.ok(gls).build();
	}
	@GET
	@Path("")
	public Response getAllGeoLocSpots(){
		return Response.ok(read.getAllNamedLocations()).build();
	}
	@GET
	@Path("{id}")
	public Response getGeoLocSpotById(@PathParam("id") String id){
		return Response.ok(read.getGeoLocSpotById(id)).build();
	}
	@GET
	@Path("/near/{lat}/{lon}")
	public Response getPostsNearYou(@PathParam("lat")String lat, @PathParam("lon") String lon){
		GeoPosition queryPos = new GeoPosition();
		queryPos.setLatitude(Double.parseDouble(lat));
		queryPos.setLongitude(Double.parseDouble(lon));
		return Response.ok(read.readNearPosts(queryPos)).build();
	}
	@GET
	@Path("/nearposts/{locname}")
	public Response getPostsByLocName(@PathParam("locname")String locName){
		return Response.ok(read.readPostsByNamedLocation(locName)).build();
	}
	// TODO maybe further operations are needed

}
