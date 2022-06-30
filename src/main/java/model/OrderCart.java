package model;

import enums.OrderCartStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCart {
    private Long id;
    private Long cartId;
    private Long productId;
    private Integer amount;
    private Double totalPrice;
    private OrderCartStatus status;

/*
* true - status (CANCELED, ORDER_SENT)
* false - status (OPEN)
* */
    private boolean deleted;

    public OrderCart(Long cartId, Long productId, Integer amount, Double totalPrice, OrderCartStatus status, boolean deleted) {
        this.cartId = cartId;
        this.productId = productId;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.status = status;
        this.deleted = deleted;
    }
}
