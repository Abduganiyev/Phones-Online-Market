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
        Response<List<Category>> response = categoryRepository.saveAll(c);
        return response;
    }

    @Override
    public Response<List<Category>> findAll() throws SQLException {
        Response<List<Category>> response = categoryRepository.findAll();
        return response;
    }

    @Override
    public Response<List<Category>> findAllSub() throws SQLException {
        Response<List<Category>> response = categoryRepository.findAllSub();
        return response;
    }

    @Override
    public Response<Category> findById() {
        return null;
    }
}
