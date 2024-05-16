package application;


	class User {
	    private static int userCount = 0;
	    private int userID;
	    private String username;
	    private String email;
	    private String password;
	    private String bio;

	    public User(String username, String email, String password, String bio) {
	        this.userID = ++userCount;
	        this.username = username;
	        this.email = email;
	        this.password = password;
	        this.bio = bio;
	    }


    public void setbio(String bio) {
        this.bio = bio;
    }
    public String getbio () {
        return bio;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    

        // Method to convert user object to comma-separated string
    public String toCSVString() {
        return userID + ",,," + username + ",,," + email + ",,," + password + ",,," + bio;
    }
}