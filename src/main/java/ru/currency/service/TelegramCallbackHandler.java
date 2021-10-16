package ru.currency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.text.ParseException;

@Service
public class TelegramCallbackHandler {

  @Autowired
  private TelegramBot telegramBot;


  public void handle(Update update,ModelManager modelManager) {
    Long chatId = update.getCallbackQuery().getMessage().getChatId();
    String queryData = update.getCallbackQuery().getData();
    try {
      modelManager.createModel(queryData);
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }


    SendMessage message = TelegramMessageBuilder.buildCommonMessage(chatId, "Сколько валюты хотите обменять?");
    telegramBot.sendAbstractMessage(message);
  }

}
