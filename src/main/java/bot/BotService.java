package bot;

import enums.BotState;
import enums.Role;
import model.User;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import repository.UserRepository;
import repository.implement.UserRepositoryImpl;
import services.UserService;
import services.implement.UserServiceImp;
import util.BotConstants;

import java.sql.SQLException;

public class BotService {
    static UserService userService = new UserServiceImp();
    // Register if new User
    public static SendMessage start(Update update) throws SQLException {
        registerUser(update);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(BotConstants.BOT_HEADER);
        sendMessage.setReplyMarkup(getMenuKeyoard());
        return null;
    }

    private static ReplyKeyboard getMenuKeyoard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        return null;
    }

    private static void registerUser(Update update) throws SQLException {
        org.telegram.telegrambots.meta.api.objects.User from = update.getMessage().getFrom();

        if (userService.findByChat_Id(update.getMessage().getChatId()) == null) {
            User user = new User(
                    (Long) null,
                    update.getMessage().getChatId(),
                    from.getFirstName(),
                    (Boolean) from.getIsBot(),
                    from.getLastName(),
                    from.getUserName(),
                    update.getMessage().getContact() != null ? update.getMessage().getContact().getPhoneNumber() : "",
                    BotState.START,
                    (long) Role.values()[2].ordinal(),
                    null
            );
            userService.save(user);
        }
    }
}
