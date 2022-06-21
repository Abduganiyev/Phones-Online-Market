package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Long id;
    private String prefix;
    private String name;
    private Timestamp created_at;
}
