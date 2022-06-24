package repository;

import dto.Response;
import enums.Role;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface RoleRepository {
    Response<Role> findById(Long id) throws SQLException;
    Response<Role> saveAll(List<Role> userList) throws SQLException;
}
