package com.sharemedia.share.controllers;

import com.sharemedia.share.DatabaseAccess;
import com.sharemedia.share.models.User;
import org.glassfish.hk2.utilities.reflection.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserController {

    public static boolean addUser(User user) {
        try (Connection connection = DatabaseAccess.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseAccess.ADD_USER)
        ) {

            statement.setInt(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger().warning(e.getMessage());
        }

        return false;
    }

    public static boolean removeUser(int id) {
        return false;
    }

    public static Optional<List<User>> getUsers() {

        try (Connection connection = DatabaseAccess.getConnection();
             Statement statement = connection.createStatement()
        ) {

            List<User> users = new ArrayList<>();

            var resultSet = statement.executeQuery(DatabaseAccess.RETRIEVE_ALL_USERS);

            while (resultSet.next()) {
                var id = resultSet.getInt(1);
                var name = resultSet.getString(2);
                var surname = resultSet.getString(3);

                users.add(new User(id, name, surname));
            }

            return Optional.of(users);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public static Optional<User> getUser(int id) {
        try (Connection connection = DatabaseAccess.getConnection();
             PreparedStatement statement = connection.prepareStatement(DatabaseAccess.RETRIEVE_USER_BY_ID)
        ) {

            statement.setInt(1, id);

            var resultSet = statement.executeQuery();

            User user = null;

            while (resultSet.next()) {

                var userID = resultSet.getInt(1);

                if (userID != id) continue;

                var name = resultSet.getString(2);
                var surname = resultSet.getString(3);

                user = new User(userID, name, surname);
            }

            return user != null ? Optional.of(user) : Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger().warning(e.getMessage());
        }

        return Optional.empty();
    }
}
