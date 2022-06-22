package services.implement;

import dto.Response;
import model.Category;
import repository.CategoryRepository;
import repository.implement.CategoryRepositoryImpl;
import services.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImp implements CategoryService {
    public static CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    @Override
    public Response<Category> save(Category c) {
        return null;
    }

    @Override
    public Response<List<Category>> saveAll(List<Category> c) throws SQLException {
        categoryRepository.saveAll(c);
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
