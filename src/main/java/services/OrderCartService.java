package services;

import dto.OrderCartDto;
import dto.Response;
import model.OrderCart;

import java.sql.SQLException;
import java.util.List;

public interface OrderCartService {
    Response<OrderCart> save(OrderCart orderCart) throws SQLException;
    Response<List<OrderCart>> findAll();
    Response<OrderCart> findById(Long id);
    Response<OrderCart> findByCartAndProduct(Long id, long productId);
    Response<List<OrderCartDto>> findAllByCartId(Long cartId) throws SQLException;
}
