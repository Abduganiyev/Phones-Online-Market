package services;

import dto.Response;
import model.Order;
import model.OrderDetail;

import java.sql.SQLException;

public interface OrderService {
    Response<Order> save(Order order) throws SQLException;
    Response<OrderDetail> saveDetail(Long orderId, Long cartId) throws SQLException;
    Response<Order> findByUserId(Long id) throws SQLException;
}
