package model;

import enums.BotState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private Long chat_id;
    private String firstname;
    private boolean isBot;
    private String lastname;
    private String username;
    private String phoneNumber;
    private BotState botState;
    private Long user_roles;
    private String created_at;
}
