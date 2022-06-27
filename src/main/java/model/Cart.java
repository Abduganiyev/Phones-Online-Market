package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private Long id;
    private Long userId;

    public Cart(Long userId) {
        this.userId = userId;
    }
}
