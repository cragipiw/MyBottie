package ru.currency.service;

import javax.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.currency.config.TelegramBotProperty;

@Service
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

  @Autowired
  private TelegramBotProperty telegramBotProperty;
  @Autowired
  private TelegramMessageHandler telegramMessageHandler;
  @Autowired
  private TelegramCallbackHandler telegramCallbackHandler;

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage()) {
      telegramMessageHandler.handle(update);
    }else if(update.hasCallbackQuery()){
      telegramCallbackHandler.handle(update);
    }
  }

  @SneakyThrows
  public void sendAbstractMessage(BotApiMethod<Message> message) {
    try {
      super.execute(message);
    } catch (TelegramApiException e) {
      log.error("Oooops", e);
    }
  }

  @Override
  public String getBotUsername() {
    return telegramBotProperty.getTelegramTokenName();
  }

  @Override
  public String getBotToken() {
    return telegramBotProperty.getTelegramTokenValue();
  }

  @PostConstruct
  private void initBot() {
    log.warn("Bot: " + getBotUsername() + "; Token: " + getBotToken());
  }

}
