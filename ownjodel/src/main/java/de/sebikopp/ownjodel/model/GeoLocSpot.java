package de.sebikopp.ownjodel.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GeoLocSpot {
	private String id;
	private String name; 
	private GeoPosition pos;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public GeoPosition getPos() {
		return pos;
	}
	public void setPos(GeoPosition pos) {
		this.pos = pos;
	}
	
}
