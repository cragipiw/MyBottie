package ru.currency.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "currency.bot.credentials")
@Data
public class TelegramBotProperty {

  private String telegramTokenName;
  private String telegramTokenValue;

}
