package ru.currency.service;

import org.springframework.stereotype.Service;



import java.text.SimpleDateFormat;

@Service
public class ConvertCurrency {

    public static String convertValue(String currencyAmount,Model model) {
        int amount;
        try {
            amount = Integer.parseInt(currencyAmount);
        } catch (NumberFormatException e) {
            return "Введите числовое значение!";
        }

        String format = "dd MMMM yyyy";
        String valueCurrency = String.format("%.2f",amount/(model.getValue()/model.getNominal()));
        String date = new SimpleDateFormat(format).format(model.getDate());

        return "Если обменять ваши рубли вы получите: " + valueCurrency + " " + model.getName() + "\nКурс на : " + date;
    }

}