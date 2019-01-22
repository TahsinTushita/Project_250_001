package com.sust.project_250_001;

import java.io.Serializable;

public class Request implements Serializable {
    private String username;
    private String bookTitle;

    public Request() {
    }

    public Request(String username, String bookTitle) {
        this.username = username;
        this.bookTitle = bookTitle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
