package com.sust.project_250_001;

import java.io.Serializable;

public class ProfileInfo implements Serializable {

    String username,address,email,booklist,wishlist;

    public ProfileInfo(){

    }

    public ProfileInfo(String username,String address,String email,String booklist,String wishlist){
        this.address = address;
        this.booklist = booklist;
        this.email = email;
        this.username = username;
        this.wishlist = wishlist;
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

    public void setBooklist(String booklist){
        this.booklist = booklist;
    }

    public String getBooklist(){
        return booklist;
    }

    public void setWishlist(){
        this.wishlist = wishlist;
    }

    public String getWishlist(){
        return wishlist;
    }

}
