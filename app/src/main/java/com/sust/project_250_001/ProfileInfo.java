package com.sust.project_250_001;

import java.io.Serializable;

public class ProfileInfo implements Serializable {

    String name;
    String username;
    String address;
    String email;

    public ProfileInfo(String name, String username, String address, String email, String profilephoto) {
        this.name = name;
        this.username = username;
        this.address = address;
        this.email = email;
        this.profilephoto = profilephoto;
    }

    String profilephoto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileInfo(){

    }

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
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
