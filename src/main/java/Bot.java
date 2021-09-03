import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.text.ParseException;

public class Bot extends TelegramLongPollingBot {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BotConfig.class);
    Model model = applicationContext.getBean(Model.class);
    ModelFiller modelFiller = applicationContext.getBean(ModelFiller.class);
    ConvertCurrency convertCurrency = applicationContext.getBean(ConvertCurrency.class);
    InlineKeyBoardChooseCurrency inlineKeyboard = applicationContext.getBean(InlineKeyBoardChooseCurrency.class);

    private int botPhase = 0;
    private String currency;
    private String userFirstName = null;
    private String userLastName = null;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (botPhase==0 && update.getMessage().getText().equals("Hello " + getBotUsername())) {
                    if (userFirstName != null && userLastName != null) {
                        try {
                            execute(new SendMessage().setText("Подождите пожалуйста, я занят другим пользователем.").setChatId(update.getMessage().getChatId()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }else {
                        try {
                            execute(inlineKeyboard.sendInlineKeyBoardChooseCurrency(update.getMessage().getChatId()));
                            userFirstName = update.getMessage().getFrom().getFirstName();
                            userLastName = update.getMessage().getFrom().getLastName();
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        botPhase++;
                    }
                } else if (botPhase == 1
                        && userFirstName.equals(update.getMessage().getFrom().getFirstName())
                        && userLastName.equals(update.getMessage().getFrom().getLastName())
                ) {
                    if(update.getMessage().getText().chars().allMatch(Character::isDigit)) {
                        try {
                            modelFiller.fillModel(currency);
                            execute(new SendMessage()
                                    .setText(convertCurrency.doConvertValue(update.getMessage().getText(), model))
                                    .setChatId(update.getMessage().getChatId()));
                        } catch (IOException | TelegramApiException | ParseException e) {
                            e.printStackTrace();
                        }
                        botPhase = 0;
                        userFirstName = null;
                        userLastName = null;
                    }else {
                        try {
                            execute(new SendMessage().setText("Введите число").setChatId(update.getMessage().getChatId()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        execute(new SendMessage().setText("Подождите пожалуйста, я занят другим пользователем.").setChatId(update.getMessage().getChatId()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (update.hasCallbackQuery()
                && userFirstName.equals(update.getCallbackQuery().getFrom().getFirstName())
                && userLastName.equals(update.getCallbackQuery().getFrom().getLastName())) {
            int messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            EditMessageText editMessageText = new EditMessageText();
            String askAmount = "Введите сколько у вас рублей";
            editMessageText.setChatId(chatId).setMessageId(messageId).setText(askAmount);
            try {
                execute(editMessageText);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            try {
                switch (update.getCallbackQuery().getData()) {
                    case "USD":
                        currency = "USD";
                        break;
                    case "EUR":
                        currency = "EUR";
                        break;
                    case "JPY":
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
        } else {
            try {
                execute(new SendMessage().setText("Подождите пожалуйста, я занят другим пользователем.").setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "CurrencyBot";
    }

    @Override
    public String getBotToken() {
        return "1870676431:AAHPHTBQcfls_ISVmFzn4u2hWj1jICaFHpA";
    }
}
