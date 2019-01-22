package com.sust.project_250_001;

import java.io.Serializable;

public class Request implements Serializable {
    private String username;
    private String bookTitle;
    private String status;

    public Request(String username, String bookTitle, String status) {
        this.username = username;
        this.bookTitle = bookTitle;
        this.status = status;
    }

    public Request() {

    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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
