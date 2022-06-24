package services;

import dto.Response;
import enums.Role;

import java.sql.SQLException;
import java.util.List;

public interface RoleService {
    Response<Role> saveAll(List<Role> roles) throws SQLException;
}
