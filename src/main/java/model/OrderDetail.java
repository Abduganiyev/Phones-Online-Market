package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    private Long id;
    private Long cartId;
    private Long productId;
    private Integer amount;
    private Double totalPrice;
}
