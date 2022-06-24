package bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
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
        Long chatId = update.getMessage().getChatId();

        if (update.hasMessage()) {
            Message message = update.getMessage();
            switch (message.getText()) {
                case "/start":
                    System.out.println(update.getMessage().getFrom());
                    execute(SendMessage.builder().text(update.getMessage().getFrom().toString()).chatId(message.getChatId().toString()).build());
                    break;
                case "/stop":
                    //execute(sendMessage.setText("Ozing qalaysan!"));
                    break;
                default:
                    //execute(nonCommand(message));
            }
        }
    }

    /*private SendMessage nonCommand(Message message) {
        Long chatId = message.getChatId();
        SendMessage sendMessage = new SendMessage().setChatId(chatId);

        if (message.hasText()) {
            switch (message.getText()) {
                case "qalaysan":
                    sendMessage.setText("Ozing Qalaysan");
                    break;
                default:
                    sendMessage.setText(message.getText() + "  " + message.getChat().getFirstName());
            }

        }
        return sendMessage;
    }*/
}
