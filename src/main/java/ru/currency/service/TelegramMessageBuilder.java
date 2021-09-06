package ru.currency.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.currency.model.CallbackData;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramMessageBuilder {

  /**
   * Построение сообщения с кнопками(прикрепленными к сообщению). Кнопки идут вертикально.
   *
   * @param chatUserId      айдишник юзера
   * @param text            сообщение
   * @param callbackButtons список коллбэков(у каждого есть текст + значение)
   * @return сообщение с кнопками и настройкаи для отправки в телеграм
   */
  public static SendMessage buildMessageWithVerticalKeyboard(Long chatUserId, String text, List<CallbackData> callbackButtons) {
    List<List<InlineKeyboardButton>> keyboardButtons = callbackButtons.stream()
        .map(button -> {
              InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(button.getText());
              inlineKeyboardButton.setCallbackData(button.getCallbackValue());
              return inlineKeyboardButton;
            }
        )
        .map(Collections::singletonList)
        .collect(Collectors.toList());

    InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardButtons);

    SendMessage sendMessage = new SendMessage(chatUserId.toString(), text);
    sendMessage.setReplyMarkup(keyboard);

    return sendMessage;
  }

}
