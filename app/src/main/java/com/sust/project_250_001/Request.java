package com.sust.project_250_001;

import java.io.Serializable;

public class Request implements Serializable {
    private String username;

    public Request(String username, String bookTitle) {
        this.username = username;
        this.bookTitle = bookTitle;
    }

    private String bookTitle;
}
