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

    @Override
    public Response<User> save(User user) throws SQLException {
        Response<User> response = userRepository.save(user);
        return response;
    }

    @Override
    public Response<User> findByChat_Id(Long chatId) throws SQLException {
        Response<User> response = userRepository.findByChat_Id(chatId);
        return response;
    }

    @Override
    public Response<User> update(User user) throws SQLException {
        Response<User> response = userRepository.update(user);
        return response;
    }
}
