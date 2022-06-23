package services.implement;

import dto.Response;
import model.Category;
import model.Product;
import repository.ProductRepository;
import repository.implement.ProductRepositoryImpl;
import services.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImp implements ProductService {
    public static ProductRepository productRepository = new ProductRepositoryImpl();

    @Override
    public Response<Product> save(Product p) {
        return null;
    }

    @Override
    public Response<List<Product>> saveAll(List<Product> p) throws SQLException {
        Response<List<Product>> response = productRepository.saveAll(p);
        return response;
    }

    @Override
    public Response<List<Product>> findAll() {
        return null;
    }

    @Override
    public Response<Product> findById() {
        return null;
    }
}
