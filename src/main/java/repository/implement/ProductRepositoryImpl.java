package repository.implement;

import dto.Response;
import model.Product;
import repository.ProductRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static config.DbConfig.connection;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Response<Product> findById(Long id) throws SQLException {
        String SELECT_BY_ID = "SELECT * FROM products WHERE id = " + id;
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new Response<>(true, "", new Product(resultSet.getLong("id"), resultSet.getString("name"),
                    resultSet.getDouble("price"), resultSet.getLong("category_id"),
                    resultSet.getString("image_url"), resultSet.getTimestamp("created_at").toString()));
        }
        return null;
    }

    @Override
    public Response<List<Product>> saveAll(List<Product> p) throws SQLException {
        String INSERT_PRODUCT = "INSERT INTO products(name,price,category_id,image_url,created_at) VALUES(?, ?, ?, ?, ?)";

        for (Product product : p) {
            if (findById(product.getId()) == null) {
                if (product.getCategoryId() != null) {

                    PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT);
                    statement.setString(1, product.getName());
                    statement.setDouble(2, product.getPrice());
                    statement.setLong(3, product.getCategoryId());
                    statement.setString(4, product.getImageUrl());
                    statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

                    statement.executeUpdate();
                }
            }
        }
        return null;
    }

    @Override
    public Response<List<Product>> findAllByCategoryID(Long id) throws SQLException {
        String SELECT_BY_ID = "SELECT * FROM products WHERE id = " + id;
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
        ResultSet resultSet = statement.executeQuery();

        List<Product> productList = new ArrayList<>();

        while (resultSet.next()) {
            productList.add(new Product(resultSet.getLong("id"), resultSet.getString("name"),
                resultSet.getDouble("price"), resultSet.getLong("category_id"),
                resultSet.getString("image_url"), resultSet.getTimestamp("created_at").toString()));
        }
        return new Response<>(true, "", productList);
    }
}
