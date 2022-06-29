package repository;

import dto.Response;
import model.OrderCart;

import java.sql.SQLException;
import java.util.List;

public interface OrderCartRepository {
    Response<OrderCart> save(OrderCart orderCart) throws SQLException;
    Response<List<OrderCart>> findAll();
    Response<OrderCart> findById(Long id);
    }
