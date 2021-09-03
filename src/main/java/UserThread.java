import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.text.ParseException;

@Component
public class UserThread extends Thread{

    String userFirstName;
    String userLastName;

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public UserThread(String userFirstName, String userLastName) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BotConfig.class);
    Model model = applicationContext.getBean(Model.class);
    ModelFiller modelFiller = applicationContext.getBean(ModelFiller.class);
    ConvertCurrency convertCurrency = applicationContext.getBean(ConvertCurrency.class);
    InlineKeyBoardChooseCurrency inlineKeyBoard = applicationContext.getBean(InlineKeyBoardChooseCurrency.class);

    @Override
    public void run() {

    }

    public SendMessage sendInlineKeyboard(Long chatId){
        return inlineKeyBoard.sendInlineKeyBoardChooseCurrency(chatId);
    }
    /*private int botPhase = 0;
    private String currency;*/

    /*public void CallbackUser(String userMessage, Update update) {
        if (update.getMessage().getText().equals("Hello " + getBotUsername())) {
            try {
                execute(sendInlineKeyBoardChooseCurrency(update.getMessage().getChatId()));
                userFirstName = update.getMessage().getFrom().getFirstName();
                userLastName = update.getMessage().getFrom().getLastName();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            botPhase++;
        } else if (botPhase == 1 && userFirstName.equals(update.getMessage().getFrom().getFirstName())
                && userLastName.equals(update.getMessage().getFrom().getLastName())
        ) {
            try {
                modelFiller.fillModel(currency);
                execute(new SendMessage()
                        .setText(convertCurrency.doConvertValue(update.getMessage().getText(), model))
                        .setChatId(update.getMessage().getChatId()));
            } catch (IOException | TelegramApiException | ParseException e) {
                e.printStackTrace();
            }
            botPhase = 0;
        } else {
            try {
                execute(new SendMessage().setText("Подожи!"));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    public void CallbackUser(CallbackQuery userCallbackQuery) {
        try {
            switch (update.getCallbackQuery().getData()) {
                case "USD":
                    execute(askingAmount(update.getCallbackQuery().getMessage().getChatId()));
                    currency = "USD";
                    break;
                case "EUR":
                    execute(askingAmount(update.getCallbackQuery().getMessage().getChatId()));
                    currency = "EUR";
                    break;
                case "JPY":
                    execute(askingAmount(update.getCallbackQuery().getMessage().getChatId()));
                    currency = "JPY";
                    break;
                default:
                    execute(new SendMessage()
                            .setText("Can't find this currency.")
                            .setChatId(update.getCallbackQuery().getMessage().getChatId()));
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }else

    {
        try {
            execute(new SendMessage().setText("Подожи!"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public static SendMessage askingAmount(long chatId){
        return new SendMessage().setChatId(chatId).setText("Введите сколько у вас рублей");
    }*/
}
