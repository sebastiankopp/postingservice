package de.sebikopp.ownjodel.jfxclient.gui;

import java.util.List;
import java.util.stream.Collectors;

import de.sebikopp.ownjodel.jfxclient.model.PostFromServer;
import de.sebikopp.ownjodel.jfxclient.request.GetAllPostsRequest;
import de.sebikopp.ownjodel.jfxclient.request.VotePostRequest;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OwnjodelJfxApp extends Application {
	private CheckBox showPostsForMyLoc = new CheckBox("Show Home Posts");
	private ObservableList<PostFromServer> currPosts = FXCollections.observableArrayList();
	private ObservableList<Object> currLocs = FXCollections.observableArrayList(); // TODO complete model classes
	private ListView<PostFromServer> listViewPosts = null;
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Ownjodel!");
		
		BorderPane bpRoot = new BorderPane();
		bpRoot.autosize();
		GridPane gpCenter = new GridPane();
		
		BorderPane bpPosts = new BorderPane();
		bpPosts.setTop(new Label("Posts"));
		listViewPosts = new ListView<PostFromServer>(currPosts);
		listViewPosts.setEditable(false);
		listViewPosts.autosize();
		listViewPosts.getSelectionModel().selectionModeProperty().set(SelectionMode.MULTIPLE);
		bpPosts.setCenter(new ScrollPane(listViewPosts));
		FlowPane leftBottom = new FlowPane(Orientation.HORIZONTAL);
		leftBottom.setAlignment(Pos.CENTER);
		Button btnrefresh = new Button("Refresh"); 
		Button btnlike = new Button("Like");
		Button btndislike = new Button("Dislike");
		Button btnShow = new Button("Show");
		btnrefresh.setOnAction(e -> performRefreshPostsOperation());
		btnlike.setOnAction(e -> performPostVote(true));
		btndislike.setOnAction(e -> performPostVote(false));
		btnShow.setOnAction(e -> showSelectedPosts());
		leftBottom.getChildren().addAll(btnShow, btnlike, btndislike, btnrefresh, showPostsForMyLoc);
		bpPosts.setBottom(leftBottom);
		gpCenter.add(bpPosts, 0, 0);
		
		BorderPane bpLocs = new BorderPane();
		bpLocs.setTop(new Label("Available Locations"));
		ListView<Object> listViewLocs = new ListView<Object>(currLocs);
		listViewLocs.autosize();
		bpLocs.setCenter(new ScrollPane(listViewLocs));
		FlowPane rightBottom = new FlowPane(Orientation.HORIZONTAL);
		rightBottom.setAlignment(Pos.CENTER);
		Button btnRefreshLocs = new Button("Refresh");
		btnRefreshLocs.setOnAction(e -> performRefreshLocsOperation());
		
		rightBottom.getChildren().add(btnRefreshLocs);
		bpLocs.setBottom(rightBottom);
		gpCenter.add(bpLocs, 1, 0);
		
		FlowPane bottomButtonBar = new FlowPane(Orientation.HORIZONTAL);
		bottomButtonBar.setAlignment(Pos.CENTER);
		Button btnPublishPost = new Button("Publish");
		btnPublishPost.setOnAction(e -> performAddPostOperation());
		Button btnEnterNewLoc = new Button("New Loc");
		btnEnterNewLoc.setOnAction(e -> performAddLocOperation());
		bottomButtonBar.getChildren().addAll(btnPublishPost, btnEnterNewLoc);
		
		
		bpRoot.setCenter(gpCenter);
		bpRoot.setBottom(bottomButtonBar);
		
		MenuItem itmSetMyLoc = new MenuItem("Edit current location");
		itmSetMyLoc.setOnAction(e -> performSetLocOperation());
		Menu menuPrefs = new Menu("Preferences", null, itmSetMyLoc);
		MenuBar menuBar = new MenuBar(menuPrefs);
		
		Scene scene = new Scene(bpRoot, 700, 500);
		bpRoot.setTop(menuBar);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	//
	private void performRefreshPostsOperation() {
		try {
			currPosts.addAll(new GetAllPostsRequest().getAllPosts());
		} catch (Exception e){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("An error occured");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			e.printStackTrace();
			alert.showAndWait();
		}
	}
	private void performRefreshLocsOperation(){
		
	}
	private void performAddPostOperation() {
		
	}
	private void performAddLocOperation() {
		
	}
	private void performSetLocOperation() {
		
	}
	private void showSelectedPosts() {
		List<PostFromServer> selectedPosts = listViewPosts.
				getSelectionModel().
				getSelectedIndices().
				stream().
				map(i -> currPosts.get(i)).collect(Collectors.toList());
				for (final PostFromServer p: selectedPosts){
					Task<Void> tsk = new Task<Void>() {
						@Override
						protected Void call() throws Exception {
							new PostViewAlert(p);
							return null;
						}
					};
					new Thread (tsk).start();
				}
	}
	private void performPostVote(boolean up){
		List<Integer> positions = listViewPosts.getSelectionModel().getSelectedIndices();
		final VotePostRequest req = new VotePostRequest();
		positions.forEach(e -> {
			String id = currPosts.get(e).getId();
			req.votePostsAsync(id, up);
		});
	}
	
}
