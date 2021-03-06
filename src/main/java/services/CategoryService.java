package services;

import dto.Response;
import model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    Response<Category> save(Category c);
    Response<List<Category>> saveAll(List<Category> c) throws SQLException;
    Response<List<Category>> findAll() throws SQLException;
    Response<List<Category>> findAllSubByID(Long id) throws SQLException;
    Response<Category> findById();
}
