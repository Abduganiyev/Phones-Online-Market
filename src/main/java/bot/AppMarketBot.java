package bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Location;
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
                        System.out.println(update.getMessage());
                        sendMessage = BotService.start(update);
                        break;
                    case BotMenu.MENU:
                        System.out.println(update.getMessage());
                        sendMessage = BotService.menu(message.getChatId());
                        break;
                    case BotMenu.CART:
                        System.out.println(update.getMessage());
                        sendMessage = BotService.showCart(message.getChatId());
                    default:

                }
            } else if (message.hasContact()) {
                Contact contact = message.getContact();

                sendMessage = BotService.getAskCurrentLocation(message.getChatId(), contact);
                execute(sendMessage);
            } else if (message.hasLocation()) {
                Location location = message.getLocation();
                location.getLongitude();
                location.getLongitude();
                sendMessage = BotService.saveOrder(message.getChatId(), location);
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
            } else if (data.contains("amount")) {
                String[] split = data.split("/");
                long productId = Long.parseLong(split[1].trim());
                int amount = Integer.parseInt(split[2].trim());

                SendMessage sendMessage = BotService.addProductToCart(message, productId , amount);

                execute(sendMessage);

                //delete old message
                DeleteMessage deleteMessage = new DeleteMessage(
                        message.getChatId().toString(),
                        message.getMessageId()
                );


                execute(deleteMessage);
            } else if (data.contains("send_order")) {
                SendMessage sendMessage = BotService.orderCommit(message);

                execute(sendMessage);

                //delete old message
                DeleteMessage deleteMessage = new DeleteMessage(
                        message.getChatId().toString(),
                        message.getMessageId()
                );


                execute(deleteMessage);
            }

        }
    }
}
