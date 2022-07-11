package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private Long id;
    private Long cartId;
    private Integer amount;
    private Double totalPrice;


    private String productName;
    private Double productPrice;

    private LocalDateTime created_at;
}
