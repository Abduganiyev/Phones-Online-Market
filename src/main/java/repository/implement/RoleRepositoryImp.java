package repository.implement;

import dto.Response;
import enums.Role;
import model.Product;
import repository.RoleRepository;
import services.RoleService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static config.DbConfig.connection;

public class RoleRepositoryImp implements RoleRepository {
    @Override
    public Response<Role> findById(Long id) throws SQLException {
        String SELECT_BY_ID = "SELECT * FROM roles WHERE id = " + id;
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new Response<>(true, "", Role.values()[Math.toIntExact(id - 1)]);
        }
        return null;
    }

    @Override
    public Response<Role> saveAll(List<Role> roleList) throws SQLException {
        String INSERT_PRODUCT = "INSERT INTO roles(name,created_at) VALUES(?, ?)";

        for (Role role : roleList) {
            if (findById((long) role.ordinal() + 1) == null) {
                PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT);
                statement.setString(1, role.name());
                statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

                statement.executeUpdate();
                }
            }
        return null;
    }
}
