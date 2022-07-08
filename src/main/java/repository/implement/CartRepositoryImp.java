package repository.implement;

import dto.Response;
import model.Cart;
import repository.CartRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static config.DbConfig.connection;

public class CartRepositoryImp implements CartRepository {
    @Override
    public Response<Cart> save(Cart cart) throws SQLException {
        String INSERT_NEW_CART = "INSERT INTO cart(user_id) VALUES (?)";
        PreparedStatement statement = connection.prepareStatement(INSERT_NEW_CART);
        statement.setLong(1, cart.getUserId());
        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public Response<Cart> findByUserId(Long id) throws SQLException {
        String SELECT_BY_USER_ID = "SELECT * FROM cart where user_id = " + id;
        PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return new Response<>(true,"",new Cart(resultSet.getLong("id"),
                    resultSet.getLong("user_id")));
        }
        return null;
    }

    @Override
    public Response<Cart> removeAll(Long id) throws SQLException {
        String DELETE_ALL_BY_CART_ID = "DELETE FROM order_cart WHERE cart_id = " + id;
        PreparedStatement statement = connection.prepareStatement(DELETE_ALL_BY_CART_ID);
        statement.executeUpdate();
        return null;
    }
}
