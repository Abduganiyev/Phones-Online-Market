package repository.implement;

import dto.Response;
import model.Category;
import repository.CategoryRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static config.DbConfig.connection;

public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public Response<Category> findById(Long id) throws SQLException {
        String SELECT_BY_ID ="SELECT * FROM category WHERE id = " + id;
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new Response<>(true,"",new Category(resultSet.getLong("id"),resultSet.getString("name"),
                    resultSet.getLong("category_id"),resultSet.getTimestamp("created_at").toString()));
        }
        return null;
    }

    @Override
    public Response<List<Category>> saveAll(List<Category> c) throws SQLException {
        String INSERT_CATEGORY = "INSERT INTO category(name,created_at,category_id) VALUES(?, ?, ?)";
        String INSERT_CATEGORY_WITHOUT_CATEGORY_ID = "INSERT INTO category(name,created_at) VALUES(?, ?)";

        for (Category category : c) {
            if (findById(category.getId()) == null) {
                if (category.getCategoryId() != null) {

                    PreparedStatement statement = connection.prepareStatement(INSERT_CATEGORY);
                    statement.setString(1, category.getName());
                    statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                    statement.setLong(3, category.getCategoryId());

                    statement.executeUpdate();
                } else {

                    PreparedStatement statement = connection.prepareStatement(INSERT_CATEGORY_WITHOUT_CATEGORY_ID);
                    statement.setString(1, category.getName());
                    statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

                    statement.executeUpdate();
                }
            }
        }


        return null;
    }

    @Override
    public Response<List<Category>> findAll() throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        String SELECT_ALL ="SELECT * FROM category";
        PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            if (resultSet.getLong("category_id") == 0) {
                categoryList.add(new Category(resultSet.getLong("id"),resultSet.getString("name"),
                        resultSet.getLong("category_id"),resultSet.getTimestamp("created_at").toString()));
            }
        }
        return new Response<>(true,"",categoryList);
    }

    @Override
    public Response<List<Category>> findAllSubByID(Long id) throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        String SELECT_ALL ="SELECT * FROM category WHERE category_id = " + id;
        PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            categoryList.add(new Category(resultSet.getLong("id"),resultSet.getString("name"),
                    resultSet.getLong("category_id"),resultSet.getTimestamp("created_at").toString()));
        }
        return new Response<>(true,"",categoryList);
    }
}
