package de.sebikopp.ownjodel.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class GeoPosition {
	@Min(-90) @Max(90)
	private double latitude;
	@Min(-180) @Max(180)
	private double longitude;
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
