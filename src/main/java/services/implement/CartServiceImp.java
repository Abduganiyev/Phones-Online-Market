package services.implement;

import dto.Response;
import model.Cart;
import model.OrderCart;
import repository.CartRepository;
import services.CartService;

import java.sql.SQLException;
import java.util.List;

public class CartServiceImp implements CartService {
    static CartRepository cartRepository = new CartRepositoryImp();
    @Override
    public Response<Cart> save(Cart cart) throws SQLException {
        Response<Cart> response = cartRepository.save(cart);
        return response;
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
        Response<Cart> response = cartRepository.findByUserId(id);
        return response;
    }
}
