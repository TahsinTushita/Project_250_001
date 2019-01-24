package com.sust.project_250_001;

import java.io.Serializable;

public class ProfileInfo implements Serializable {

    String name;
    String username;
    String address;
    String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileInfo(){

    }

    public ProfileInfo(String username,String address,String email,String name){
        this.address = address;
        this.email = email;
        this.username = username;
        this.name = name;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return address;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }


}
