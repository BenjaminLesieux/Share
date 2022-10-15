package com.sharemedia.share.models;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Comment {

    private int commentID;

    private int mediaID;

    private int clientID;

    private String content;

    private double rating;

    public Comment() {
    }

    public Comment(int commentID, int mediaID, int clientID, String content, double rating) {
        this.commentID = commentID;
        this.mediaID = mediaID;
        this.clientID = clientID;
        this.content = content;
        this.rating = rating;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getMediaID() {
        return mediaID;
    }

    public void setMediaID(int mediaID) {
        this.mediaID = mediaID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
