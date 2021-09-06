package ru.currency.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallbackData {

  private String text;
  private String callbackValue;

  public CallbackData(String textAndValue) {
    this.text = textAndValue;
    this.callbackValue = textAndValue;
  }

}
