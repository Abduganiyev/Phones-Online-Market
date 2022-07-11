package repository.implement;

import dto.OrderCartDto;
import dto.Response;
import enums.OrderStatus;
import model.Order;
import model.OrderDetail;
import repository.OrderRepository;
import services.OrderCartService;
import services.implement.OrderCartServiceImp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static config.DbConfig.connection;

public class OrderRepositoryImp implements OrderRepository {
    static OrderCartService orderCartService = new OrderCartServiceImp();
    @Override
    public Response<Order> save(Order order) throws SQLException {
        String INSERT_NEW_ORDER = "INSERT INTO \"order\"(total_price,user_id,status) VALUES(?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(INSERT_NEW_ORDER);
        statement.setDouble(1,order.getTotalPrice());
        statement.setLong(2,order.getUserId());
        statement.setString(3,order.getStatus().name());
        statement.executeUpdate();

        String SELECT_LAST_ORDER = "SELECT * FROM \"order\" ORDER BY created_at DESC";
        PreparedStatement statement2 = connection.prepareStatement(SELECT_LAST_ORDER);
        ResultSet resultSet = statement2.executeQuery();
        if (resultSet.next()) {
            return new Response<>(true,"",new Order(resultSet.getLong("id"),
                    resultSet.getDouble("total_price"),OrderStatus.valueOf(resultSet.getString("status")),
                    resultSet.getLong("user_id"),resultSet.getTimestamp("created_at").toLocalDateTime()));
        }

        return new Response<>(false,"",null);
    }

    @Override
    public Response<OrderDetail> saveDetail(Long orderId, Long cartId) throws SQLException {
        String SELECT_ALL_BY_CART_ID = "SELECT * FROM order_cart WHERE cart_id = " + cartId;
        PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_CART_ID);
        ResultSet resultSet = statement.executeQuery();

        String SAVE_DETAILS = "INSERT INTO order_detail(order_id, order_cart_id, amount, \"totalPrice\") VALUES(?,?,?,?)";
        PreparedStatement statement2 = connection.prepareStatement(SAVE_DETAILS);
        while (resultSet.next()) {
            statement2.setLong(1, orderId);
            statement2.setLong(2, resultSet.getLong("id"));
            statement2.setInt(3, resultSet.getInt("amount"));
            statement2.setDouble(4, resultSet.getDouble("total_price"));
            statement2.executeUpdate();
        }

        return null;
    }

    @Override
    public Response<Order> findByUserId(Long id) throws SQLException {
        String SELECT_USER_BY_ID = "SELECT * FROM \"order\" WHERE user_id =" + id;
        PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new Response<>(true,"",new Order(resultSet.getLong("id"),resultSet.getDouble("total_price"),
                    OrderStatus.valueOf(resultSet.getString("status")),
                    resultSet.getLong("user_id"),resultSet.getTimestamp("created_at").toLocalDateTime()));
        }
        return null;
    }
}
