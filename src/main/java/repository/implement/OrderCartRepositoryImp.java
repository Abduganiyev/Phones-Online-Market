package repository.implement;

import dto.OrderCartDto;
import dto.Response;
import enums.OrderCartStatus;
import model.OrderCart;
import repository.OrderCartRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        statement.setBoolean(6,orderCart.getDeleted());

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

    @Override
    public Response<List<OrderCartDto>> findAllByCartId(Long cartId) throws SQLException {
        String SELECT_ALL_BY_CART_ID = "SELECT oc.id,\n" +
                "       oc.amount,\n" +
                "       oc.total_price,\n" +
                "       oc.created_at,\n" +
                "       p.name,\n" +
                "       p.price\n" +
                "FROM order_cart oc\n" +
                "    join products p on p.id = oc.product_id WHERE oc.cart_id = " + cartId;
        List<OrderCartDto> orderCarts = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_CART_ID);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            orderCarts.add(new OrderCartDto(
               resultSet.getLong("id"),
               resultSet.getInt("amount"),
               resultSet.getDouble("total_price"), resultSet.getString("name"),
               resultSet.getDouble("price"),
               resultSet.getTimestamp("created_at").toLocalDateTime()
            ));
        }

        return new Response<>(true,"",orderCarts);
    }
}
