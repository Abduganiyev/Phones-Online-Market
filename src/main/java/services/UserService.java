package services;

import dto.Response;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    Response<User> saveAll(List<User> userList) throws SQLException;

    Response<User> save(User user) throws SQLException;

    Response<User> findByChat_Id(Long chatId) throws SQLException;

    Response<User> update(User user) throws SQLException;
}
