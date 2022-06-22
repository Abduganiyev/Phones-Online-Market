package services;

import dto.Response;
import model.User;

public interface UserService {
    Response<User> creat(User u);
}
