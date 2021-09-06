package ru.currency.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.currency.enums.CurrencyType;
import ru.currency.model.CallbackData;

@Service
public class TelegramMessageHandler {

  @Autowired
  private TelegramBot telegramBot;

  public void handle(Update update) {
    String text = update.getMessage().getText().toLowerCase();
    Long userId = update.getMessage().getFrom().getId();

    if (text.startsWith("привет")) {
      sendTypes(userId);
    }

  }

  private void sendTypes(Long userId) {
    List<CallbackData> callbacks = CurrencyType.getAllTypes().stream()
        .map(CallbackData::new)
        .collect(Collectors.toList());

    SendMessage message = TelegramMessageBuilder.buildMessageWithVerticalKeyboard(userId, "Выберите тип", callbacks);

    telegramBot.sendAbstractMessage(message);
  }

}
