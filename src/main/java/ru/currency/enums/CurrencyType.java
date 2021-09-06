package ru.currency.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CurrencyType {

  USD,
  EUR,
  JPY;

  public static List<String> getAllTypes() {
    return Stream.of(CurrencyType.values())
        .map(Enum::toString)
        .collect(Collectors.toList());
  }

}
