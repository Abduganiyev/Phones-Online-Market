package bot;

import dto.Response;
import enums.BotState;
import enums.Role;
import model.Category;
import model.Product;
import model.User;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import services.CategoryService;
import services.ProductService;
import services.UserService;
import services.implement.CategoryServiceImp;
import services.implement.ProductServiceImp;
import services.implement.UserServiceImp;
import util.BotConstants;
import util.BotMenu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BotService {
    static UserService userService = new UserServiceImp();
    static CategoryService categoryService = new CategoryServiceImp();
    static ProductService productService = new ProductServiceImp();
    // Register if new User
    public static SendMessage start(Update update) throws SQLException {
        registerUser(update);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(BotConstants.BOT_HEADER);
        sendMessage.setReplyMarkup(getMenuKeyoard());
        return sendMessage;
    }

    private static ReplyKeyboard getMenuKeyoard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton(BotMenu.MENU));
        rows.add(keyboardRow1);

        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow2.add(new KeyboardButton(BotMenu.CART));
        keyboardRow2.add(new KeyboardButton(BotMenu.SETTINGS));
        rows.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
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

    public static SendMessage menu(Long chatId) throws SQLException {
        Response<User> response = userService.findByChat_Id(chatId);
        if (response != null) {
            User user = response.getObject();
            user.setBotState(BotState.SHOW_MENU);
            userService.update(user);
        }

        Response<List<Category>> categoryAll = categoryService.findAll();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(BotConstants.BOT_HEADER);
        sendMessage.setReplyMarkup(getInlineKeyboards(categoryAll.getObject()));

        return sendMessage;
    }

    private static InlineKeyboardMarkup getInlineKeyboards(List<Category> categories) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboards = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        Iterator<Category> iterator = categories.iterator();
        while (iterator.hasNext()) {
            Category next = iterator.next();
            buttons = new ArrayList<>();

            InlineKeyboardButton button1 = new InlineKeyboardButton(next.getName());
            button1.setCallbackData("category/" + next.getId().toString());
            buttons.add(button1);
            if (iterator.hasNext()) {
                    next = iterator.next();
                    InlineKeyboardButton button2 = new InlineKeyboardButton(next.getName());
                    button2.setCallbackData("category/" + next.getId().toString());
                    buttons.add(button2);
            }

            inlineKeyboards.add(buttons);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboards);
        return inlineKeyboardMarkup;
    }

    private static InlineKeyboardMarkup getInlineKeyboardsProduct(List<Product> products) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboards = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product next = iterator.next();
            buttons = new ArrayList<>();

            InlineKeyboardButton button1 = new InlineKeyboardButton(next.getName());
            button1.setCallbackData("product/" + next.getId().toString());
            buttons.add(button1);
            if (iterator.hasNext()) {
                next = iterator.next();
                InlineKeyboardButton button2 = new InlineKeyboardButton(next.getName());
                button2.setCallbackData("product/" + next.getId().toString());
                buttons.add(button2);
            }

            inlineKeyboards.add(buttons);
        }

        inlineKeyboardMarkup.setKeyboard(inlineKeyboards);
        return inlineKeyboardMarkup;
    }
    public static EditMessageText showSubCategoriesByID(Message message,Update update) throws SQLException {
        String data = update.getCallbackQuery().getData();


        Response<User> response = userService.findByChat_Id(message.getChatId());
        if (response != null) {
            User user = response.getObject();
            user.setBotState(BotState.SHOW_CATEGORIES);
            userService.update(user);
        }

        Response<List<Category>> productResponse = categoryService.findAllSubByID(Long.parseLong(data));

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setParseMode(ParseMode.MARKDOWN);
        editMessageText.setText(BotConstants.BOT_HEADER);

        editMessageText.setReplyMarkup(getInlineKeyboards(productResponse.getObject()));

        return editMessageText;
    }

    public static EditMessageText showProductsByCategory(Message message,Update update) throws SQLException {
        String data = update.getCallbackQuery().getData();
        Long categoryId = Long.getLong(data);


        Response<User> response = userService.findByChat_Id(message.getChatId());
        if (response != null) {
            User user = response.getObject();
            user.setBotState(BotState.SHOW_PRODUCTS);
            userService.update(user);
        }

        Response<List<Product>> productResponse = productService.findAllByCategoryID(categoryId);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setParseMode(ParseMode.MARKDOWN);
        editMessageText.setText(BotConstants.BOT_HEADER);

        editMessageText.setReplyMarkup(getInlineKeyboardsProduct(productResponse.getObject()));

        return editMessageText;
    }
}
