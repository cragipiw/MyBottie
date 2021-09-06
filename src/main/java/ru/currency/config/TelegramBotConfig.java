package ru.currency.config;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.currency.service.TelegramBot;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TelegramBotConfig {

  private final TelegramBot telegramBot;

  @PostConstruct
  public void registerTelegramBot() {
    try {
      TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
      botsApi.registerBot(telegramBot);
    } catch (TelegramApiException e) {
      log.error("Registration bot exception {}", e.toString());
    }
  }

}
