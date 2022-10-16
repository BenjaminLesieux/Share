package com.sharemedia.share.controllers;

import com.sharemedia.share.DatabaseAccess;
import com.sharemedia.share.models.Comment;
import com.sharemedia.share.models.ItemCategory;
import com.sharemedia.share.models.MediaItem;
import org.glassfish.hk2.utilities.reflection.Logger;

import javax.print.attribute.standard.Media;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MediaItemController {

    public static boolean addMediaItem(MediaItem mediaItem) {

        try (Connection connection = DatabaseAccess.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DatabaseAccess.ADD_MEDIA_ITEM)) {

            preparedStatement.setInt(1, mediaItem.getMediaID());
            preparedStatement.setString(2, mediaItem.getName());
            preparedStatement.setString(3, mediaItem.getDescription());
            preparedStatement.setString(4, mediaItem.getCity());
            preparedStatement.setString(5, mediaItem.getAuthor());
            preparedStatement.setInt(6, mediaItem.getYear());
            preparedStatement.setInt(7, mediaItem.getUserID());
            preparedStatement.setInt(8, mediaItem.getCategoryID());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean removeMediaItem(int mediaID, int userID) {
        Optional<List<MediaItem>> mediaItems = getAllMediaItems();

        if (mediaItems.isPresent()) {
            for (MediaItem media : mediaItems.get()) {
                if (media.getMediaID() == mediaID) {
                    if (media.getUserID() != userID) return false;
                }
            }
        }

        try (Connection connection = DatabaseAccess.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseAccess.REMOVE_COMMENTS_OF_MEDIA_ITEM)) {

            statement.setInt(1, mediaID);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try (Connection connection = DatabaseAccess.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseAccess.REMOVE_MEDIA_ITEM)) {

            statement.setInt(1, mediaID);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public static Optional<List<MediaItem>> getAllMediaItems() {
        try (Connection connection = DatabaseAccess.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseAccess.RETRIEVE_ALL_MEDIA_ITEMS)) {

            var mediaItems = extractFromResultSet(statement);
            return Optional.of(mediaItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<MediaItem> getMediaItem(int id) {
        try (Connection connection = DatabaseAccess.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseAccess.RETRIEVE_MEDIA_ITEM_BY_ID)) {

            statement.setInt(1, id);

            List<MediaItem> mediaItems = extractFromResultSet(statement);
            MediaItem mediaItem = !mediaItems.isEmpty() ? mediaItems.get(0) : null;
            return mediaItem != null ? Optional.of(mediaItem) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public static Optional<List<MediaItem>> getMediaItemsBySearch(String search) {
        try (Connection connection = DatabaseAccess.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseAccess.RETRIEVE_ALL_ITEMS_WITH_KEYWORD)) {

            statement.setString(1, search);

            List<MediaItem> mediaItems = extractFromResultSet(statement);
            return Optional.of(mediaItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<List<MediaItem>> getMediaItemsOfCategory(int categoryID) {
        try (Connection connection = DatabaseAccess.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseAccess.RETRIEVE_ALL_ITEMS_BY_CATEGORY)) {

            statement.setInt(1, categoryID);
            var resultSet = statement.executeQuery();

            List<MediaItem> mediaItems = extractFromResultSet(statement);
            return Optional.of(mediaItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public static Optional<List<MediaItem>> getMediaItemsFromCity(String city) {
        try (Connection connection = DatabaseAccess.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseAccess.RETRIEVE_ALL_ITEMS_FROM_CITY)) {

            statement.setString(1, city);
            var resultSet = statement.executeQuery();

            List<MediaItem> mediaItems = extractFromResultSet(statement);
            return Optional.of(mediaItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public static Optional<List<MediaItem>> getMediaItemsFromUser(int userID) {
        try (Connection connection = DatabaseAccess.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseAccess.RETRIEVE_ALL_ITEMS_FROM_USER)) {

            statement.setInt(1, userID);
            var resultSet = statement.executeQuery();

            List<MediaItem> mediaItems = extractFromResultSet(statement);

            return Optional.of(mediaItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private static List<MediaItem> extractFromResultSet(PreparedStatement statement) throws SQLException {
        List<MediaItem> mediaItems = new ArrayList<>();

        var resultSet = statement.executeQuery();

        while (resultSet.next()) {
            var mediaID = resultSet.getInt("media_id");
            var clientID = resultSet.getInt("client_id");
            var name = resultSet.getString("name");
            var description = resultSet.getString("description");
            var city = resultSet.getString("city");
            var author = resultSet.getString("author");
            var year = resultSet.getInt("year_date");
            var categoryID = resultSet.getInt("category_id");

            mediaItems.add(new MediaItem(mediaID, clientID, name, description, city, author, year, categoryID, null));
        }

        return mediaItems;
    }
}
