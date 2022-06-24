package repository;

import dto.Response;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    Response<User> findById(Long id) throws SQLException;
    Response<User> saveAll(List<User> userList) throws SQLException;
}
