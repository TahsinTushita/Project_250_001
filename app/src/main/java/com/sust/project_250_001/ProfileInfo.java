package com.sust.project_250_001;

import java.io.Serializable;

public class ProfileInfo implements Serializable {

    String username,address,email,parent;

    public ProfileInfo(){

    }

    public ProfileInfo(String username,String address,String email){
        this.address = address;
        this.email = email;
        this.username = username;
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
