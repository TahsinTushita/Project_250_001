package com.sust.project_250_001;

public class BookReview {
    String username;
    String postDesc;
    String postTitle;

    public BookReview() {

    }

    public BookReview(String username, String postDesc, String postTitle) {
        this.username = username;
        this.postDesc = postDesc;
        this.postTitle = postTitle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
}
