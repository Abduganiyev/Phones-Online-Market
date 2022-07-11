package services.implement;

import dto.OrderCartDto;
import dto.Response;
import model.OrderCart;
import repository.OrderCartRepository;
import repository.implement.OrderCartRepositoryImp;
import services.OrderCartService;

import java.sql.SQLException;
import java.util.List;

public class OrderCartServiceImp implements OrderCartService {
    static OrderCartRepository orderCartRepository = new OrderCartRepositoryImp();
    @Override
    public Response<OrderCart> save(OrderCart orderCart) throws SQLException {
        Response<OrderCart> response = orderCartRepository.save(orderCart);
        return response;
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
        orderCartRepository.findByCartAndProduct(id,productId);
        return null;
    }

    @Override
    public Response<List<OrderCartDto>> findAllByCartId(Long cartId) throws SQLException {
        Response<List<OrderCartDto>> response = orderCartRepository.findAllByCartId(cartId);
        return response;
    }

    @Override
    public Response<OrderCart> findAllByCartIdAndDelete(Long id) throws SQLException {
        Response<OrderCart> response = orderCartRepository.findAllByCartIdAndDelete(id);
        return response;
    }

    @Override
    public Response<Double> getSum(Long id) throws SQLException {
        Response<Double> response = orderCartRepository.getSum(id);
        return response;
    }
}
