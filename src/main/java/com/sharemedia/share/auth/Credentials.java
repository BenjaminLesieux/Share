package com.sharemedia.share.auth;

public class Credentials {

    private int userID;

    private String password;

    public Credentials() {
    }

    public Credentials(int userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
