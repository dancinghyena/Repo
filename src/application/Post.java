 package application;
import java.time.LocalDate;
import java.time.LocalTime;

public class Post 
{private static int postCount = 0;
private int postID;
private String postAuthorName;
private LocalDate postCreationDate;
private LocalTime postCreationTime;
private String postContent;
private boolean like;
public Post(String postAuthorName, LocalDate postCreationDate, LocalTime postCreationTime, String postContent) {
    this.postID = ++postCount;
    this.postAuthorName = postAuthorName;
    this.postCreationDate = postCreationDate;
    this.postCreationTime = postCreationTime;
    this.postContent = postContent;
}

       public int getPostID() {
		return postID;
	}

	public void setPostID(int postID) {
		this.postID = postID;
	}



    // Getter and setter methods for postContent
    public String getPostContent() {
        return postContent;
    }

    public boolean isLike() {
		return like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    // Getter method for dateCreated
    public LocalDate getpostCreationDate() {
        return postCreationDate;
    }
    
    public LocalTime getpostCreationTime() {
        return postCreationTime;
    }

        // Method to convert user object to comma-separated string
    public String toCSVString() {
        return postAuthorName + ",,," + postCreationDate + ",,," + postCreationTime + ",,," + postContent + ",,," + postID;
    }
    
}