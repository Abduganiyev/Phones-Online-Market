package services;

import dto.Response;
import model.Cart;
import model.OrderCart;

import java.sql.SQLException;
import java.util.List;

public interface CartService {
    Response<Cart> save(Cart cart) throws SQLException;
    Response<List<Cart>> findAll();
    Response<Cart> findById(Long id);
    Response<Cart> findByUserId(Long id) throws SQLException;
    Response<Cart> removeAll(Long id) throws SQLException;
}
