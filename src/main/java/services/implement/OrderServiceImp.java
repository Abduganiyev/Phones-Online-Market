package services.implement;

import dto.Response;
import model.Order;
import model.OrderDetail;
import repository.OrderRepository;
import repository.implement.OrderRepositoryImp;
import services.OrderService;

import java.sql.SQLException;

public class OrderServiceImp implements OrderService {
    static OrderRepository orderRepository = new OrderRepositoryImp();
    @Override
    public Response<Order> save(Order order) throws SQLException {
        Response<Order> response = orderRepository.save(order);
        return response;
    }

    @Override
    public Response<OrderDetail> saveDetail(Long orderId, Long cartId) throws SQLException {
        Response<OrderDetail> response = orderRepository.saveDetail(orderId,cartId);
        return response;
    }

    @Override
    public Response<Order> findByUserId(Long id) throws SQLException {
        Response<Order> response = orderRepository.findByUserId(id);
        return response;
    }
}
