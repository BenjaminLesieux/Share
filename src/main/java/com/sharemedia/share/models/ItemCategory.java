package com.sharemedia.share.models;

import java.util.Objects;

public enum ItemCategory {

    TEXT(0, "Text"), MUSIC(1, "Music"), MOVIE(2, "Movie"), PDF(3, "PDF");

    int id;
    String value;

    ItemCategory(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
