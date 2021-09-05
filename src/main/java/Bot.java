import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
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
    private User userInitiator = null;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    private SendMessage firstUserMessageResponse(Message message){
        SendMessage sendMessage;
            sendMessage = inlineKeyboard.sendInlineKeyBoardChooseCurrency(message.getChatId());
            userInitiator = message.getFrom();
            botPhase++;
            return sendMessage;
    }

    private int getCurrencyAmount(String message)throws NumberFormatException{
        return Integer.parseInt(message.substring(message.indexOf(" ")+1));
    }

    private SendMessage userCurrencyResponse(Message message) throws IOException, ParseException{
        SendMessage responseMessage = new SendMessage();
        try{
            modelFiller.fillModel(currency);
            responseMessage.setText(convertCurrency.doConvertValue(getCurrencyAmount(message.getText()), model)).setChatId(message.getChatId());
            botPhase = 0;
            userInitiator = null;
            return responseMessage;
        }catch (NumberFormatException e){
            return replyMessage(message.getChatId(),Messages.WrongNumFormat.getTitle());
        }
    }

    private SendMessage handleMessage(Message message) throws TelegramApiException, IOException, ParseException {
        if (message.getText().endsWith("привет!") && userInitiator==null && botPhase == 0) return firstUserMessageResponse(message);
        else if(message.getFrom().equals(userInitiator) && botPhase == 1) return replyMessage(message.getChatId(),Messages.CHOOSE.getTitle());
        else if(message.getFrom().equals(userInitiator) && botPhase == 2) return userCurrencyResponse(message);
        else if(!userInitiator.equals(message.getFrom()) && botPhase == 2) return replyMessage(message.getChatId(),Messages.BUSY.getTitle());
        return new SendMessage().setText("Непонятно!").setChatId(message.getChatId());
    }

    private SendMessage editCallbackQuery(CallbackQuery callbackQuery) throws TelegramApiException {
        EditMessageText editMessageText = new EditMessageText();

        int messageId = callbackQuery.getMessage().getMessageId();
        long chatId = callbackQuery.getMessage().getChatId();
        currency = callbackQuery.getData();
        execute(editMessageText.setChatId(chatId).setMessageId(messageId).setText(currency));
        botPhase++;
        return replyMessage(callbackQuery.getMessage().getChatId(),Messages.MoneyValue.getTitle());
    }

    private SendMessage replyMessage(long chatId,String type){
        return new SendMessage().setText(type).setChatId(chatId);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if(message.hasText() && message.getText().startsWith(getBotUsername())) {
                try {
                    execute(handleMessage(message));
                } catch (IOException | TelegramApiException | ParseException e) {
                    e.printStackTrace();
                }
            }

        } else if (update.hasCallbackQuery() && userInitiator.equals(update.getCallbackQuery().getFrom())) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            try {
                execute(editCallbackQuery(callbackQuery));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            try {
                execute(replyMessage(update.getCallbackQuery().getMessage().getChatId(),Messages.BUSY.getTitle()));
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
