package services.implement;

import dto.Response;
import model.Cart;
import repository.CartRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static config.DbConfig.connection;

public class CartRepositoryImp implements CartRepository {
    @Override
    public Response<Cart> save(Cart cart) throws SQLException {
        String INSERT_NEW_CART = "";
        PreparedStatement statement = connection.prepareStatement(INSERT_NEW_CART);
        return null;
    }

    @Override
    public Response<List<Cart>> findAll() {
        return null;
    }

    @Override
    public Response<Cart> findById(Long id) {
        return null;
    }

    @Override
    public Response<Cart> findByUserId(Long id) {
        return null;
    }
}
