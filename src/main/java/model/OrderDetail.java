package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    private Long id;
    private Long cartId;
    private Long productId;
    private Integer amount;
    private Double totalPrice;
    private LocalDateTime created_at;
}
