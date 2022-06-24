package services.implement;

import com.google.gson.Gson;
import enums.Role;
import model.Category;
import model.Product;
import model.User;
import services.*;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreDataToDbFromJsonImp implements StoreDataToDbFromJson {
    public static void store() throws SQLException {
        Gson gson = new Gson();

        List<Category> categoryList = new ArrayList<>();

        try(BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/Categories.json"))) {
            categoryList.addAll(Arrays.asList(gson.fromJson(bf,Category[].class)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CategoryService categoryService = new CategoryServiceImp();
        categoryService.saveAll(categoryList);

        List<Product> producList = new ArrayList<>();

        try(BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/Products.json"))) {
            producList.addAll(Arrays.asList(gson.fromJson(bf, Product[].class)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ProductService productService = new ProductServiceImp();
        productService.saveAll(producList);

        List<User> userList = new ArrayList<>();

        try(BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/User.json"))) {
            userList.addAll(Arrays.asList(gson.fromJson(bf, User[].class)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserService userService = new UserServiceImp();
        userService.saveAll(userList);

        List<Role> roleList = new ArrayList<>(Arrays.asList(Role.values()));

        RoleService roleService = new RoleServiceImp();
        roleService.saveAll(roleList);
    }
}
