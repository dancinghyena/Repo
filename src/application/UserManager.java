package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserManager {
    private static String loggedInUsername;
    private List<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
        Map<String, String[]> usersMap = FileCRUD.readUsersFile();
        // This line iterates over the map entries, creating new User objects and adding them to the users list.
        usersMap.forEach((email, userDetails) -> {
            if (userDetails.length == 4) { // Ensure there are 4 elements after splitting
                users.add(new User(userDetails[1], email, userDetails[2], userDetails[3]));
            }
        });
    }
    public void registerUser(String username, String email, String password, String bio) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username " + username + " is already taken. Please choose a different one.");
                return;
            }
            if (user.getEmail().equals(email)) {
                System.out.println("Email " + email + " is already registered. Please use a different email.");
                return;
            }
        }
        User newUser = new User(username, password, email, bio);
        users.add(newUser);
        FileCRUD.addUser(newUser);
    }

    public boolean loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUsername = username;
                return true;
            }
        }
        return false;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public void setLoggedInUsername(String loggedInUsername) {
        UserManager.loggedInUsername = loggedInUsername;
    }
}
