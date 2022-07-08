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

    private Double currentLatitude;
    private Double currentLongitude;

    public User(Long chat_id, String firstname, boolean isBot, String lastname, String username, String phoneNumber, BotState botState, Long user_roles) {
        this.chat_id = chat_id;
        this.firstname = firstname;
        this.isBot = isBot;
        this.lastname = lastname;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.botState = botState;
        this.user_roles = user_roles;
    }
}
