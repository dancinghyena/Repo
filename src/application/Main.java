package application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

	private UserManager userManager = new UserManager();
	private Stage primaryStage;
	private Scene userScene, postsScene;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(20));

		Label usernameLabel = new Label("Username");
		TextField usernameField = new TextField();
		usernameField.setPromptText("Username");

		Label emailLabel = new Label("Email");
		TextField emailField = new TextField();
		emailField.setPromptText("Email");

		Label passwordLabel = new Label("Password");
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Password");

		Button loginButton = new Button("Login");
		Button registerButton = new Button("Register");

		HBox buttonLayout = new HBox(10, loginButton, registerButton);
		buttonLayout.setAlignment(Pos.CENTER);

		layout.getChildren().addAll(usernameLabel, usernameField, emailLabel, emailField, passwordLabel, passwordField,
				buttonLayout);

		Scene scene = new Scene(layout, 400, 400);
		primaryStage.setTitle("ASU Social");
		primaryStage.setScene(scene);
		primaryStage.show();

		registerButton.setOnAction(e -> {
			String username = usernameField.getText();
			String email = emailField.getText();
			String password = passwordField.getText();
			userManager.registerUser(username, email, password, "");
			switchToUserScene(username);
		});

		loginButton.setOnAction(e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();
			if (userManager.loginUser(username, password)) {
				switchToUserScene(username);
			} else {
				showAlert("Login failed. Invalid username or password.");
			}
		});
	}

	private void switchToUserScene(String username) {
		VBox userLayout = new VBox(20);
		userLayout.setAlignment(Pos.CENTER);
		userLayout.setPadding(new Insets(20));

		Label welcomeLabel = new Label(username +  ", Welcome bro "  );
		welcomeLabel.setFont(new Font(24));

		TextField postField = new TextField();
		postField.setPromptText("What's on your mind?");
		postField.setMaxWidth(300);

		Button postButton = new Button("Post");
		postButton.setFont(new Font(16));
		postButton.setPadding(new Insets(10, 20, 10, 20));

		Button seePostsButton = new Button("See Posts");
		seePostsButton.setFont(new Font(16));
		seePostsButton.setPadding(new Insets(10, 20, 10, 20));

		HBox buttonLayout = new HBox(20, postButton, seePostsButton);
		buttonLayout.setAlignment(Pos.CENTER);

		userLayout.getChildren().addAll(welcomeLabel, postField, buttonLayout);

		postButton.setOnAction(e -> {
			String postContent = postField.getText();
			Post post = new Post(username, LocalDate.now(), LocalTime.now(), postContent);
			FileCRUD.addPost(post);
			postField.clear();
		});

		seePostsButton.setOnAction(e -> switchToPostsScene(username));

		userScene = new Scene(userLayout, 400, 400);
		primaryStage.setScene(userScene);
	}

	private void switchToPostsScene(String username) {
		VBox postsLayout = new VBox(20);
		postsLayout.setAlignment(Pos.CENTER);
		postsLayout.setPadding(new Insets(20));

		List<Post> posts = FileCRUD.readPostsFile();
		posts.forEach(post -> {
			VBox postBox = new VBox(5);
			postBox.setStyle("-fx-border-color: black; -fx-background-color: white; -fx-padding: 10;");

			Label postContentLabel = new Label(post.getPostContent());
			postContentLabel.setFont(new Font(16));
			postContentLabel.setWrapText(true);

			Label postDateLabel = new Label(post.getpostCreationDate() + " at " + post.getpostCreationTime());
			postDateLabel.setFont(new Font(12));

			postBox.getChildren().addAll(postContentLabel, postDateLabel);
			postsLayout.getChildren().add(postBox);
		});

		ScrollPane scrollPane = new ScrollPane(postsLayout);
		scrollPane.setFitToWidth(true);

		Button backButton = new Button("Back");
		backButton.setFont(new Font(16));
		backButton.setPadding(new Insets(10, 20, 10, 20));
		backButton.setOnAction(e -> primaryStage.setScene(userScene));

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(scrollPane);
		borderPane.setBottom(backButton);
		BorderPane.setAlignment(backButton, Pos.CENTER);
		BorderPane.setMargin(backButton, new Insets(20, 0, 20, 0));

		postsScene = new Scene(borderPane, 400, 400);
		primaryStage.setScene(postsScene);
	}

	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR, message);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
