package com.example.springbootwebdemo.reptile.domain;

public class JdGoods {
    private int id;
    private String name;
    private String author;
    private String price;
    private String publisher;
    private String imgurl;
    private String publishtime;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getImgurl() {
        return imgurl;
    }
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
    public String getPublishtime() {
        return publishtime;
    }
    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }
}
