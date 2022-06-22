package model;

import enums.BotState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private Long chatId;
    private String firstname;
    private String lastname;
    private String username;
    private String phoneNumber;
    private BotState botState;
    private String created_at;
}
