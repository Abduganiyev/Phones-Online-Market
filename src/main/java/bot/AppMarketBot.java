package bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import util.BotMenu;
import util.BotSettings;

import java.util.List;

public class AppMarketBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return BotSettings.USER_NAME_BOT;
    }

    @Override
    public String getBotToken() {
        return BotSettings.TOKEN;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage sendMessage = null;

            if (message.hasText()) {
                String text = message.getText();
                switch (text) {
                    case BotMenu.START:
                        System.out.println(update.getMessage().getContact());
                        sendMessage = BotService.start(update);
                        break;
                    case "/stop":

                        break;
                    default:

                }
            }

            execute(sendMessage);


        } else if (update.hasCallbackQuery()) {

        }
    }
}
