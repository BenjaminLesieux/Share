package com.sharemedia.share.controllers;

import com.sharemedia.share.DatabaseAccess;
import com.sharemedia.share.models.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentController {

    public static boolean addComment(Comment comment, int mediaID) {

        try (Connection connection = DatabaseAccess.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseAccess.ADD_COMMENT)) {

            comment.setMediaID(mediaID);

            statement.setInt(1, comment.getCommentID());
            statement.setInt(2, comment.getClientID());
            statement.setInt(3, comment.getMediaID());
            statement.setString(4, comment.getContent());
            statement.setDouble(5, comment.getRating());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean removeComment(Comment comment) {
        return false;
    }

    public static Optional<List<Comment>> getCommentsFromMediaItem(int mediaID) {
        try (Connection connection = DatabaseAccess.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseAccess.RETRIEVE_ALL_COMMENTS_FROM_MEDIA)) {

            statement.setInt(1, mediaID);
            var resultSet = statement.executeQuery();

            List<Comment> comments = new ArrayList<>();

            while (resultSet.next()) {
                var commentID = resultSet.getInt("comments_id");
                var clientID = resultSet.getInt("client_id");
                var content = resultSet.getString("content");
                var rating = resultSet.getDouble("rating");

                var comment = new Comment(commentID, clientID, mediaID, content, rating);
                comments.add(comment);
            }

            return Optional.of(comments);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
