package de.sebikopp.ownjodel.jfxclient.model;

import java.util.List;

public class PostFromServer {
	private String id;
	private String content;
	private String title;
	private List<PostFromServer> subposts;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public List<PostFromServer> getSubposts() {
		return subposts;
	}
	public void setSubposts(List<PostFromServer> subposts) {
		this.subposts = subposts;
	}
	@Override
	public String toString(){
		return title;
	}
}
