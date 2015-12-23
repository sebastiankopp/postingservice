package de.sebikopp.ownjodel.model;

import java.time.LocalDateTime;
import java.util.List;

public class Post {
	private String id;
	private String content;
	private String title;
	private List<Post> subposts;
	private LocalDateTime ldt;
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
	public LocalDateTime getLdt() {
		return ldt;
	}
	public void setLdt(LocalDateTime ldt) {
		this.ldt = ldt;
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
	
	
}
