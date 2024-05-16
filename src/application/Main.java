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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private UserManager userManager = new UserManager();
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Labels and Text fields for user input
        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // Buttons for login and register
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        // Layout for buttons
        HBox buttonLayout = new HBox(10, loginButton, registerButton);
        buttonLayout.setAlignment(Pos.CENTER);

        // Adding components to the main layout
        layout.getChildren().addAll(usernameLabel, usernameField, emailLabel, emailField, passwordLabel, passwordField, buttonLayout);

        // Scene and stage setup
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setTitle("ASU Social");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Register button action
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            userManager.registerUser(username, email, password, "");
            switchToUserScene(username);
        });

        // Login button action
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
        // User scene layout
        VBox userLayout = new VBox(10);
        userLayout.setAlignment(Pos.CENTER);
        userLayout.setPadding(new Insets(20));

        Label welcomeLabel = new Label("Welcome, " + username);
        TextField postField = new TextField();
        postField.setPromptText("What's on your mind?");
        Button postButton = new Button("Post");
        Button seePostsButton = new Button("See Posts");

        userLayout.getChildren().addAll(welcomeLabel, postField, postButton, seePostsButton);

        // Post button action
        postButton.setOnAction(e -> {
            String postContent = postField.getText();
            Post post = new Post(username, LocalDate.now(), LocalTime.now(), postContent);
            FileCRUD.addPost(post);
            postField.clear();
        });

        // See Posts button action
        seePostsButton.setOnAction(e -> switchToPostsScene());

        Scene userScene = new Scene(userLayout, 400, 400);
        primaryStage.setScene(userScene);
    }

    private void switchToPostsScene() {
        // Posts scene layout
        VBox postsLayout = new VBox(10);
        postsLayout.setAlignment(Pos.CENTER);
        postsLayout.setPadding(new Insets(20));

        List<Post> posts = FileCRUD.readPostsFile();
        posts.forEach(post -> {
            Label postLabel = new Label(post.getPostContent() + " - " + post.getpostCreationDate() + " at " + post.getpostCreationTime());
            postsLayout.getChildren().add(postLabel);
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(primaryStage.getScene()));
        postsLayout.getChildren().add(backButton);

        Scene postsScene = new Scene(postsLayout, 400, 400);
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
