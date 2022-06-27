package services;

import dto.Response;
import model.Cart;

import java.util.List;

public interface CartService {
    Response<Cart> save(Cart cart);
    Response<List<Cart>> findAll();
    Response<Cart> findById(Long id);

    Response<Cart> findByUserId(Long id);
}
