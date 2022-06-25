package enums;

public enum BotState {
    START,
    SHOW_MENU,
    SHOW_PRODUCTS,
    SHOW_CATEGORIES;

    public static BotState fromString(String name) {
        for (BotState value : BotState.values()) {
            if (value.name().equals(name))
                return value;
        }
        return null;
    }
}
