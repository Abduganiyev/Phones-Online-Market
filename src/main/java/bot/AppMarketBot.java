package bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import util.BotMenu;
import util.BotSettings;

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
                        System.out.println(update.getMessage().getFrom());
                        sendMessage = BotService.start(update);
                        break;
                    case BotMenu.MENU:
                        sendMessage = BotService.menu(message.getChatId());
                        break;
                    default:

                }
            }

            execute(sendMessage);


        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Message message = update.getCallbackQuery().getMessage();
            EditMessageText editMessageText = null;
            if (data.contains("category")) {
                    editMessageText = BotService.showSubCategoriesByID(message,update);
                    execute(editMessageText);
            } else if (data.contains("subCategory")) {
                editMessageText = BotService.showProductsByCategory(message,update);
                execute(editMessageText);
            } else if (data.contains("product")) {
                SendPhoto sendPhoto = BotService.showProductInfoByID(message,update);
                execute(sendPhoto);
            }



        }
    }
}
