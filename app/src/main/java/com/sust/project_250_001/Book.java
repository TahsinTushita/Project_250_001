package com.sust.project_250_001;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable{
    String title;
    String author;
    String isbn;
    String topReview;
    String parent;
    String imgurl;
    String users;

    public Book(String title, String author, String isbn, String topReview, String parent, String imgurl,String users) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.topReview = topReview;
        this.parent = parent;
        this.imgurl = imgurl;
        this.users = users;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getImgurl() {

        return imgurl;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Book() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String  getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTopReview() {
        return topReview;
    }

    public void setTopReview(String topReview) {
        this.topReview = topReview;
    }

    public String getUsers(){
        return users;
    }

}
