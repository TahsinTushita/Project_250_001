package com.sust.project_250_001;

import java.io.Serializable;

public class Request implements Serializable {
    private String username;
    private String bookTitle;


    public void setStatus(long status) {
        this.status = status;
    }

    private long status;

    public long getStatus() {
        return status;
    }

    private String parent;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Request(String username, String bookTitle, long status) {
        this.username = username;
        this.bookTitle = bookTitle;
        this.status = status;
    }

    public Request() {

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
