package dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCartDto {
    /**
     * ORDER_CART DATA
     */
    private Long id;
    private Integer amount;
    private Double cartTotalPrice;

    /**
     * PRODUCT DATA
     */
    private String productName;
    private Double productPrice;

    private LocalDateTime createdAt;
}
