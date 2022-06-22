package services.implement;

import dto.Response;
import model.Category;
import services.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImp implements ProductService {
    @Override
    public Response<Category> save(Category c) {
        return null;
    }

    @Override
    public Response<List<Category>> saveAll(List<Category> c) throws SQLException {
        return null;
    }

    @Override
    public Response<List<Category>> findAll() {
        return null;
    }

    @Override
    public Response<Category> findById() {
        return null;
    }
}
