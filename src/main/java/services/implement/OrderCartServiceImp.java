package services.implement;

import dto.Response;
import model.OrderCart;
import services.OrderCartService;

import java.sql.SQLException;
import java.util.List;

public class OrderCartServiceImp implements OrderCartService {
    @Override
    public Response<OrderCart> save(OrderCart orderCart) throws SQLException {
        return null;
    }

    @Override
    public Response<List<OrderCart>> findAll() {
        return null;
    }

    @Override
    public Response<OrderCart> findById(Long id) {
        return null;
    }
}
