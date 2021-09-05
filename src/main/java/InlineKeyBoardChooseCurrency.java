import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyBoardChooseCurrency{
    public SendMessage sendInlineKeyBoardChooseCurrency(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("USD").setCallbackData("USD"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("EUR").setCallbackData("EUR"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("JPY").setCallbackData("JPY"));

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setText("Здавствуйте!\nВыберите валюту").setReplyMarkup(inlineKeyboardMarkup);
    }
}
