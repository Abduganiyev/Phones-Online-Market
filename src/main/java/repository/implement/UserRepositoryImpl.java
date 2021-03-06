package repository.implement;

import dto.Response;
import enums.BotState;
import model.User;
import repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static config.DbConfig.connection;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Response<User> findByChat_Id(Long id) throws SQLException {
        String SELECT_BY_ID = "SELECT * FROM users WHERE chat_id = " + id;
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new Response<>(true, "", new User(resultSet.getLong("id"), resultSet.getLong("chat_id"),
                    resultSet.getString("first_name"),resultSet.getBoolean("is_bot"), resultSet.getString("last_name"),
                    resultSet.getString("username"),resultSet.getString("phone_number"),BotState.fromString(resultSet.getString("bot_state")),
                    resultSet.getLong("user_roles"),resultSet.getString("created_at"), 0d,0d));
        }
        return null;
    }

    @Override
    public Response<User> saveAll(List<User> userList) throws SQLException {
        String INSERT_USER = "INSERT INTO users(chat_id,first_name,last_name,username,phone_number," +
                "bot_state,created_at,user_roles) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        for (User user : userList) {
            if (findByChat_Id(user.getChat_id()) == null) {

                PreparedStatement statement = connection.prepareStatement(INSERT_USER);
                statement.setLong(1, user.getChat_id());
                statement.setString(2, user.getFirstname());
                statement.setString(3, user.getLastname());
                statement.setString(4, user.getUsername());
                statement.setString(5, user.getPhoneNumber());
                statement.setString(6, user.getBotState().toString());
                statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                //statement.setBoolean(8, user.isBot());
                statement.setLong(8, user.getUser_roles());

                statement.executeUpdate();
                }
            }
        return null;
    }

    @Override
    public Response<User> save(User user) throws SQLException {
        String INSERT_USER = "INSERT INTO users(chat_id,first_name,last_name,username,phone_number," +
                "bot_state,created_at,user_roles) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(INSERT_USER);
        statement.setLong(1, user.getChat_id());
        statement.setString(2, user.getFirstname());
        statement.setString(3, user.getLastname());
        statement.setString(4, user.getUsername());
        statement.setString(5, user.getPhoneNumber());
        statement.setString(6, user.getBotState().toString());
        statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        //statement.setBoolean(8, user.isBot());
        statement.setLong(8, user.getUser_roles());

        try {
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        String SELECT_LAST_USER = "SELECT * FROM users WHERE chat_id = " + user.getChat_id();
        statement = connection.prepareStatement(SELECT_LAST_USER);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return new Response<>(true, "", new User(resultSet.getLong("id"), resultSet.getLong("chat_id"),
                        resultSet.getString("first_name"), resultSet.getBoolean("is_bot"), resultSet.getString("last_name"),
                        resultSet.getString("username"), resultSet.getString("phone_number"), BotState.fromString(resultSet.getString("bot_state")),
                        resultSet.getLong("user_roles"), resultSet.getString("created_at"),0d,0d));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Response<User> update(User user) throws SQLException {
        String UPDATE_USER_DATA = "UPDATE users SET first_name = ?, last_name = ?, username = ?,phone_number = ?, bot_state = ?," +
                "longitude = ?, latitude = ?" +
                "WHERE id = " + user.getId();

        PreparedStatement statement = connection.prepareStatement(UPDATE_USER_DATA);
        statement.setString(1, user.getFirstname());
        statement.setString(2, user.getLastname());
        statement.setString(3, user.getUsername());
        statement.setString(4, user.getPhoneNumber());
        statement.setString(5, user.getBotState().name());
        statement.setDouble(6, user.getCurrentLatitude());
        statement.setDouble(7, user.getCurrentLongitude());
        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
