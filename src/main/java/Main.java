import bot.AppMarketBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import services.implement.StoreDataToDbFromJsonImp;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        StoreDataToDbFromJsonImp.store();

        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(new AppMarketBot());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
