package application;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileCRUD {

    public static void addUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(user.toCSVString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addPost(Post post) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("posts.txt", true))) {
            writer.write(post.toCSVString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String[]> readUsersFile() {
        Map<String, String[]> usersMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] userDetails = line.split(",,,");
                    if (userDetails.length == 5) { // Ensure there are 5 elements after splitting
                        usersMap.put(userDetails[2], new String[]{userDetails[0], userDetails[1], userDetails[3], userDetails[4]});
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersMap;}

    public static List<Post> readPostsFile() {
        List<Post> posts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("posts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] postDetails = line.split(",,,");
                    Post post = new Post(postDetails[0], LocalDate.parse(postDetails[1]), LocalTime.parse(postDetails[2]), postDetails[3]);
                    posts.add(post);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public static int getNewUserID() {
        Map<String, String[]> usersMap = readUsersFile();
        return usersMap.values().stream().mapToInt(details -> Integer.parseInt(details[0])).max().orElse(0) + 1;
    }
}