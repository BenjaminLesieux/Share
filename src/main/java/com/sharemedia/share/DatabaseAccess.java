package com.sharemedia.share;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseAccess {

    public static String DATABASE_URL = "jdbc:sqlite:/Users/benjaminlesieux/Desktop/efrei/M1/Intégration Systèmes - Fondamentaux/Project/Share/src/main/resources/share_database";

    public static String RETRIEVE_ALL_USERS = "select * from Client";

    public static String RETRIEVE_USER_BY_ID = "select * from Client where client_id = ?";

    public static String RETRIEVE_ALL_MEDIA_ITEMS = """
        select M.media_id, M.name, M.description, M.city, M.author, M.year_date, M.client_id, M.category_id from MediaItem M
    """;

    public static String RETRIEVE_MEDIA_ITEM_BY_ID = "select * from MediaItem where media_id = ?";

    public static String RETRIEVE_ALL_COMMENTS_FROM_MEDIA = """
            select C.comments_id, C.client_id, C.media_id, C.content, C.rating from Comments C
                  inner join MediaItem MI on MI.media_id = C.media_id
                  where MI.media_id = ?
    """;

    public static String RETRIEVE_ALL_COMMENTS_FROM_USER = """
            select * from Comment C
                inner join Client Cl on  Cl.client_id = C.client_id
                where Cl.client_id = ?
    """;

    public static String RETRIEVE_ALL_ITEMS_FROM_USER = """
            select * from MediaItem M
                where M.client_id = ?
    """;

    public static String RETRIEVE_ALL_ITEMS_FROM_CITY = """
            select * from MediaItem M
                where M.city like ?
    """;

    public static String RETRIEVE_ALL_ITEMS_BY_CATEGORY = """
            select * from MediaItem M
                inner join ItemCategory I on I.category_id = M.category_id
                where I.category_id = ?
    """;

    public static String RETRIEVE_ALL_ITEMS_WITH_KEYWORD = """
            select * from MediaItem M
                where M.name like ?
    """;

    public static String ADD_USER = "insert into Client values (?, ?, ?, ?)";

    public static String ADD_MEDIA_ITEM = "insert into MediaItem values (?, ?, ?, ?, ?, ?, ?, ?)";

    public static String ADD_COMMENT = "insert into Comments values (?, ?, ?, ?, ?)";

    public static Connection getConnection() throws SQLException {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(DATABASE_URL);
        return ds.getConnection();
    }
}
