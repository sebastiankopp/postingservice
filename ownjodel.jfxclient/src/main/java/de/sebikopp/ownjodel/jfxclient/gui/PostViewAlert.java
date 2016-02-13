package de.sebikopp.ownjodel.jfxclient.gui;

import de.sebikopp.ownjodel.jfxclient.model.PostFromServer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

class PostViewAlert extends Alert{
	private PostFromServer post;
	
	public PostViewAlert(PostFromServer _post){
		super(AlertType.NONE);
		if (post == null){
			close();
		}
		this.post = _post;
		GridPane majorPane = new GridPane();
		GridPane minorPane = new GridPane();
		TextField viewPostTitle = new TextField(this.post.getTitle());
		TextField countUpvotes = new TextField("");
		TextField countDownvotes = new TextField("");
		minorPane.add(new Label("Title"), 0, 0);
		minorPane.add(viewPostTitle, 1, 0);
		minorPane.add(new Label("Upvotes"), 0, 1);
		minorPane.add(countUpvotes, 1, 1);
		minorPane.add(new Label("Downvotes"), 2, 0);
		minorPane.add(countDownvotes, 2, 1);
		
		majorPane.add(minorPane, 0, 0);
		TextArea viewPostContent = new TextArea(this.post.getContent());
		majorPane.add(viewPostContent, 0, 1);
		ButtonType btnLeave = new ButtonType("Leave", ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().setAll(btnLeave);
		getDialogPane().setExpandableContent(viewPostContent);
		setTitle(post.getTitle());
		setHeaderText(null);
		
		showAndWait();
	}
}
