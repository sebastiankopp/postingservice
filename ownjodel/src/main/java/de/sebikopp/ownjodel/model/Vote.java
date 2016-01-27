package de.sebikopp.ownjodel.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Vote {
	private boolean up;
	private String jsessionid;
	public boolean isUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public String getJsessionid() {
		return jsessionid;
	}
	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}
	
}
