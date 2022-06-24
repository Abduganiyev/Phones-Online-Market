package services;

import dto.Response;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    Response<User> saveAll(List<User> userList) throws SQLException;
}
