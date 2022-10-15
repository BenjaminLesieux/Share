package com.sharemedia.share.models;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@XmlRootElement
public class MediaItem {

    private int mediaID;

    private int userID;

    private String name;
    private String description;
    private String city;
    private String author;

    private int year;

    private int categoryID;

    private List<Comment> comments;

    public MediaItem() {
    }

    public MediaItem(int mediaID, int userID, String name, String description, String city, String author, int year, int categoryID, List<Comment> comments) {
        this.mediaID = mediaID;
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.city = city;
        this.author = author;
        this.year = year;
        this.categoryID = categoryID;
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public ItemCategory getCategory() {
        Optional<ItemCategory> category = Arrays.stream(ItemCategory.values())
                .filter(itemCategory -> itemCategory.id == this.categoryID)
                .findFirst();

        return category.orElse(null);
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getMediaID() {
        return mediaID;
    }

    public void setMediaID(int mediaID) {
        this.mediaID = mediaID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
