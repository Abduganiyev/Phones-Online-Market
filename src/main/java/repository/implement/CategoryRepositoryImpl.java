package repository.implement;

import config.DbConfig;
import dto.Response;
import model.Category;
import repository.CategoryRepository;

import java.awt.event.WindowStateListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {
    public static Connection connection;

    static {
        try {
            connection = DbConfig.connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Response<List<Category>> saveAll(List<Category> c) throws SQLException {

        for (Category category : c) {
            String INSERT_CATEGORY = "INSERT_INTO category(name,created_at,category_id) VALUES(?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(INSERT_CATEGORY);
            statement.setString(1, category.getName());
            statement.setLong(2, category.getCategoryId());
            statement.setTimestamp(3, Timestamp.valueOf(category.getCreated_at()));
        }


        return null;
    }
}
