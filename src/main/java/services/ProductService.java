package services;

import dto.Response;
import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    Response<Product> save(Product p);
    Response<List<Product>> saveAll(List<Product> p) throws SQLException;
    Response<List<Product>> findAllByCategoryID(Long id) throws SQLException;
    Response<Product> findById(Long id) throws SQLException;
}
