package repository.implement;

import dto.Response;
import model.Category;
import repository.ProductRepository;

import java.sql.SQLException;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Response<Category> findById(Long id) throws SQLException {
        return null;
    }

    @Override
    public Response<List<Category>> saveAll(List<Category> c) throws SQLException {
        return null;
    }
}
