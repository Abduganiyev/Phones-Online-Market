package repository.implement;

import dto.Response;
import model.Cart;
import model.OrderCart;
import repository.OrderCartRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static config.DbConfig.connection;

public class OrderCartRepositoryImp implements OrderCartRepository {
    @Override
    public Response<OrderCart> save(OrderCart orderCart) throws SQLException {
        String INSERT_NEW_ORDER_CART = "INSERT INTO order_cart(cart_id,product_id,amount,total_price,status,deleted) " +
                "VALUES (?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(INSERT_NEW_ORDER_CART);
        statement.setLong(1,orderCart.getCartId());
        statement.setLong(2,orderCart.getProductId());
        statement.setInt(3,orderCart.getAmount());
        statement.setDouble(4,orderCart.getTotalPrice());
        statement.setString(5,orderCart.getStatus().name());
        statement.setBoolean(6,orderCart.isDeleted());

        try {

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Response<OrderCart>(true, "saved",null);
    }

    @Override
    public Response<List<OrderCart>> findAll() {
        return null;
    }

    @Override
    public Response<OrderCart> findById(Long id) {
        return null;
    }

    @Override
    public Response<OrderCart> findByCartAndProduct(Long id, long productId) {

        return null;
    }
}
