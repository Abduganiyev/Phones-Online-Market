package repository;

import dto.Response;
import model.Category;
import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductRepository {
    Response<Product> findById(Long id) throws SQLException;
    Response<List<Product>> saveAll(List<Product> c) throws SQLException;

    Response<List<Product>> findAllByCategoryID(Long id) throws SQLException;
}
