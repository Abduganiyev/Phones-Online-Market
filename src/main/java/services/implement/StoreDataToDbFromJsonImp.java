package services.implement;

import com.google.gson.Gson;
import model.Category;
import services.StoreDataToDbFromJson;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreDataToDbFromJsonImp implements StoreDataToDbFromJson {
    public static void store(){
        Gson gson = new Gson();

        List<Category> categoryList = new ArrayList<>();

        try(BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/Categories.json"))) {
            categoryList.addAll(Arrays.asList(gson.fromJson(bf,Category[].class)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Category category : categoryList) {
            System.out.println(category);
        }
    }
}
