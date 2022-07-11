package model;

import enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private Double totalPrice;
    private OrderStatus status;
    private Long userId;
    private LocalDateTime created_at;

    public Order(Double totalPrice, OrderStatus status, Long userId) {
        this.totalPrice = totalPrice;
        this.status = status;
        this.userId = userId;
    }
}
