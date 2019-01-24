package com.sust.project_250_001;

import java.util.ArrayList;

public class Users {

    String username;
    int availability;

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getAvailability() {
        return availability;
    }

    public Users(String username, int availability) {
        this.username = username;
        this.availability = availability;
    }

    public Users(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
