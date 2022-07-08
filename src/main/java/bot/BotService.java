package bot;

import dto.OrderCartDto;
import dto.Response;
import enums.BotState;
import enums.OrderCartStatus;
import enums.Role;
import model.*;
import model.User;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import services.*;
import services.implement.*;
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
    static CartService cartService = new CartServiceImp();
    static OrderCartService orderCartService = new OrderCartServiceImp();
    // Register if new User
    public static SendMessage start(Update update) throws SQLException {
        registerUser(update);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(BotConstants.BOT_HEADER);
        sendMessage.setReplyMarkup(getMenuKeyboard());
        return sendMessage;
    }

    private static ReplyKeyboard getMenuKeyboard() {
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
                    update.getMessage().getChatId(),
                    from.getFirstName(),
                    (Boolean) from.getIsBot(),
                    from.getLastName(),
                    from.getUserName(),
                    update.getMessage().getContact() != null ? update.getMessage().getContact().getPhoneNumber() : "",
                    BotState.START,
                    (long) Role.values()[2].ordinal()
            );
            Response<User> last_saved = userService.save(user);

            /*
            * CREATE CART FOR NEW USER
            */
            if (cartService.findByUserId(last_saved.getObject().getId()) == null) {
                cartService.save(new Cart(last_saved.getObject().getId()));
            }
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
    private static InlineKeyboardMarkup getInlineKeyboardsSubCategory(List<Category> categories) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboards = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        Iterator<Category> iterator = categories.iterator();
        while (iterator.hasNext()) {
            Category next = iterator.next();
            buttons = new ArrayList<>();

            InlineKeyboardButton button1 = new InlineKeyboardButton(next.getName());
            button1.setCallbackData("subCategory/" + next.getId().toString());
            buttons.add(button1);
            if (iterator.hasNext()) {
                next = iterator.next();
                InlineKeyboardButton button2 = new InlineKeyboardButton(next.getName());
                button2.setCallbackData("subCategory/" + next.getId().toString());
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

        long l = Long.parseLong(data.substring(9));
        Response<List<Category>> productResponse = categoryService.findAllSubByID(l);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setParseMode(ParseMode.MARKDOWN);
        editMessageText.setText(BotConstants.BOT_HEADER);

        editMessageText.setReplyMarkup(getInlineKeyboardsSubCategory(productResponse.getObject()));

        return editMessageText;
    }

    public static EditMessageText showProductsByCategory(Message message,Update update) throws SQLException {
        String data = update.getCallbackQuery().getData();


        Response<User> response = userService.findByChat_Id(message.getChatId());
        if (response != null) {
            User user = response.getObject();
            user.setBotState(BotState.SHOW_PRODUCTS);
            userService.update(user);
        }

        long categoryId = Long.parseLong(data.substring(12));
        Response<List<Product>> productResponse = productService.findAllByCategoryID(categoryId);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setParseMode(ParseMode.MARKDOWN);
        editMessageText.setText(BotConstants.BOT_HEADER);

        editMessageText.setReplyMarkup(getInlineKeyboardsProduct(productResponse.getObject()));

        return editMessageText;
    }

    public static SendPhoto showProductInfoByID(Message message, Update update) throws SQLException {
        String data = update.getCallbackQuery().getData();


        Response<User> response = userService.findByChat_Id(message.getChatId());
        if (response != null) {
            User user = response.getObject();
            user.setBotState(BotState.SELECT_PRODUCT);
            userService.update(user);
        }

        long productId = Long.parseLong(data.substring(8));
        Response<Product> productResponse = productService.findById(productId);

        SendPhoto sendPhoto = new SendPhoto();
        if (productResponse != null) {
            sendPhoto.setChatId(message.getChatId().toString());
            sendPhoto.setPhoto(new InputFile(productResponse.getObject().getImageUrl()));
            sendPhoto.setCaption(productResponse.getObject().getName() + "\n\nPrice: " + productResponse.getObject().getPrice() +
                    "$\n\nMiqdorini  tanlang");

        /*
            9 INLINE BUTTONS FOR COUNT OF ORDER
        */

            sendPhoto.setReplyMarkup(getInlineKeyboardsForOrder(productId));
        }
        return sendPhoto;
    }

    private static ReplyKeyboard getInlineKeyboardsForOrder(Long productId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineKeyboards = new ArrayList<>();

        int counter = 1;
        for (int i = 0; i < 3; i++) {
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                InlineKeyboardButton button = new InlineKeyboardButton(counter + "ta");
                button.setCallbackData("amount/" + productId + "/" + counter);
                buttons.add(button);
                counter++;
            }
            inlineKeyboards.add(buttons);
        }
        inlineKeyboardMarkup.setKeyboard(inlineKeyboards);

        return inlineKeyboardMarkup;
    }

    public static SendMessage addProductToCart(Message message, long productId, Integer amount) throws SQLException {
        Response<User> response = userService.findByChat_Id(message.getChatId());
        if (response != null) {
            User user = response.getObject();
            user.setBotState(BotState.SELECT_PRODUCT_COUNT);
            userService.update(user);
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Maxsulot savatchaga qo'shildi!");

        if (response == null) {
            sendMessage.setText("USER NOT FOUND");
            return sendMessage;
        }

        Response<Product> productResponse = productService.findById(productId);
        if(productResponse == null) {
            sendMessage.setText("PRODUCT NOT FOUND {"+ productId +"}");
            return sendMessage;
        }

        Response<Cart> cart = cartService.findByUserId(response.getObject().getId());
        if (cart == null) {
            sendMessage.setText("CART NOT FOUNT WITH USER {"+ response.getObject().getFirstname() + " " + response.getObject().getLastname() +"}");
            return sendMessage;
        }

        Response<OrderCart> orderCartResponse = orderCartService.findByCartAndProduct(cart.getObject().getId(),productId);
        if(orderCartResponse == null) {
            orderCartService.save(new OrderCart(cart.getObject().getId(),
                    productId,amount,
                    (productResponse.getObject().getPrice() * amount),
                    OrderCartStatus.OPEN,
                    false));
        }
        return sendMessage;
    }

    public static SendMessage showCart(Long chatId) throws SQLException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText("");

        Response<User> response = userService.findByChat_Id(chatId);
        if (response != null) {
            User user = response.getObject();
            user.setBotState(BotState.SHOW_CART);
            userService.update(user);
        } else {
            sendMessage.setText("USER NOT FOUND");
            return sendMessage;
        }

        Response<Cart> cartResponse = cartService.findByUserId(response.getObject().getId());
        if(cartResponse == null) {
            sendMessage.setText("CART NOT FOUND");
            return sendMessage;
        }

        Response<List<OrderCartDto>> orderCartResponse = orderCartService.findAllByCartId(cartResponse.getObject().getId());
        if (orderCartResponse.getObject().isEmpty()) {
            sendMessage.setText("Xarid savatchasi bo'sh");
            return sendMessage;
        }

        String text = "\n\nSavatchada\n\n";

        double total = 0d;
        for (int i = 1; i < orderCartResponse.getObject().size(); i++) {
            OrderCartDto dto = orderCartResponse.getObject().get(i - 1);

            text += i + ".\t" + dto.getProductName() + " X " + dto.getAmount() + " = " + (dto.getAmount() * dto.getProductPrice()) + "\n";

            total += dto.getAmount() * dto.getProductPrice();

        }

        text += "\n Jami:  " + total + " $";

        sendMessage.setText(text);
        sendMessage.setReplyMarkup(getInlineKeyboardsForCart(orderCartResponse.getObject()));

        return sendMessage;
    }

    private static ReplyKeyboard getInlineKeyboardsForCart(List<OrderCartDto> orderCarts) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list = new ArrayList<>();

        Iterator<OrderCartDto> iterator = orderCarts.iterator();
        while (iterator.hasNext()) {
            OrderCartDto orderCartDto = iterator.next();

            List<InlineKeyboardButton> buttons = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("❌ " + orderCartDto.getProductName());
            inlineKeyboardButton.setCallbackData(BotConstants.ORDER_CART_DELETE + BotConstants.PREFIX + orderCartDto.getId());
            buttons.add(inlineKeyboardButton);

            if (iterator.hasNext()) {
                orderCartDto = iterator.next();

                inlineKeyboardButton = new InlineKeyboardButton("❌ " + orderCartDto.getProductName());
                inlineKeyboardButton.setCallbackData(BotConstants.ORDER_CART_DELETE + BotConstants.PREFIX + orderCartDto.getId());
                buttons.add(inlineKeyboardButton);
            }

            list.add(buttons);
        }

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        InlineKeyboardButton sedOrder = new InlineKeyboardButton("✅ Buyurtmani Jo'natish ");
        sedOrder.setCallbackData("send_order");
        buttons.add(sedOrder);

        InlineKeyboardButton canselOrder = new InlineKeyboardButton("\uD83D\uDED1 Bokor qilish ");
        canselOrder.setCallbackData("cancel_order");
        buttons.add(canselOrder);

        list.add(buttons);
        buttons = new ArrayList<>();
        InlineKeyboardButton home = new InlineKeyboardButton("\uD83C\uDFE0 Menu ga qaytish ");
        home.setCallbackData("back_menu");
        buttons.add(home);

        list.add(buttons);

        inlineKeyboardMarkup.setKeyboard(list);
        return inlineKeyboardMarkup;
    }

    public static SendMessage orderCommit(Message message) throws SQLException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("");

        Response<User> response = userService.findByChat_Id(message.getChatId());
        if (response != null) {
            User user = response.getObject();
            user.setBotState(BotState.ORDER_COMMIT);
            userService.update(user);
        } else {
            sendMessage.setText("USER NOT FOUND");
            return sendMessage;
        }

        sendMessage.setText("Raqamingzni Jo'nating");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("\uD83D\uDCF1 Raqamni Jo'natish");
        button.setRequestContact(true);
        row.add(button);
        rowList.add(row);
        replyKeyboardMarkup.setKeyboard(rowList);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public static SendMessage getAskCurrentLocation(Long chatId, Contact contact) throws SQLException {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText("Adressni jo'nating");


        Response<User> response = userService.findByChat_Id(chatId);
        if (response != null) {
            User user = response.getObject();
            user.setPhoneNumber(contact.getPhoneNumber());
            userService.update(user);
        } else {
            sendMessage.setText("USER NOT FOUND");
            return sendMessage;
        }

        // TODO: 05.07.2022 User ga contact dagi Telefon raqamini save qilib qo'yish

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);

        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("\uD83D\uDCCD Locatsiya Jo'natish");
        button.setRequestLocation(true);

        row.add(button);
        rowList.add(row);
        replyKeyboardMarkup.setKeyboard(rowList);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }

    public static SendMessage saveOrder(Long chatId, Location location) throws SQLException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setChatId(chatId.toString());

        Response<User> response = userService.findByChat_Id(chatId);
        if (response != null) {
            User user = response.getObject();
            user.setBotState(BotState.ORDER_SENT);
            user.setCurrentLatitude(location.getLatitude());
            user.setCurrentLongitude(location.getLongitude());
            userService.update(user);
        } else {
            sendMessage.setText("USER NOT FOUND");
            return sendMessage;
        }
        Response<Cart> cartResponse = cartService.findByUserId(response.getObject().getId());
        if(cartResponse == null) {
            sendMessage.setText("CART NOT FOUND");
            return sendMessage;
        }
        cartService.removeAll(cartResponse.getObject().getId());
        return null;
    }
}
