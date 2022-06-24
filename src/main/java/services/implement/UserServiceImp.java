package services.implement;

import dto.Response;
import model.User;
import repository.UserRepository;
import repository.implement.UserRepositoryImpl;
import services.UserService;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImp implements UserService {

    public static UserRepository userRepository = new UserRepositoryImpl();
    @Override
    public Response<User> saveAll(List<User> userList) throws SQLException {
        Response<User> response = userRepository.saveAll(userList);
        return response;
    }
}
