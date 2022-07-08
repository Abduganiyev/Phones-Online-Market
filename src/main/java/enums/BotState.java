package enums;

public enum BotState {
    START,
    SHOW_MENU,
    SHOW_PRODUCTS,
    SHOW_CATEGORIES,
    SELECT_PRODUCT,
    SELECT_PRODUCT_COUNT,
    SHOW_CART,
    ORDER_COMMIT,
    ORDER_SENT;

    public static BotState fromString(String name) {
        for (BotState value : BotState.values()) {
            if (value.name().equals(name))
                return value;
        }
        return null;
    }
}
