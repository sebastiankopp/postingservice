package de.sebikopp.ownjodel.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Post {
	private String id;
	private String content;
	private String title;
	private List<Post> subposts;
	private LocalDateTime dateTime;
	private GeoPosition pos;
	private List<Vote> votes;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Post> getSubposts() {
		return subposts;
	}
	public void setSubposts(List<Post> subposts) {
		this.subposts = subposts;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public GeoPosition getPos() {
		return pos;
	}
	public void setPos(GeoPosition pos) {
		this.pos = pos;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Vote> getVotes() {
		return votes;
	}
	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}
	@Override
	public boolean equals (Object o){
		if (o instanceof Post){
			return ((Post) o).getId().equals(id);
		} else return false;
	}
	
}
