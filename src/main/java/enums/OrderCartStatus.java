package enums;

public enum OrderCartStatus {
    OPEN,
    CANCELED,
    ORDER_SENT;

    public static OrderCartStatus fromName(String name) {
        for (OrderCartStatus value : OrderCartStatus.values()) {
            if (value.name().equals(name))
                return value;
        }
        return null;
    }

}
