package repository;

import dto.Response;
import model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryRepository {
    Response<Category> findById(Long id) throws SQLException;
    Response<List<Category>> saveAll(List<Category> c) throws SQLException;
}
