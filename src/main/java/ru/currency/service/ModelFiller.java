package ru.currency.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Service

public class ModelFiller {
@Autowired
private JsonReceiver jsonReceiver;

    public void fillModel(String convertValue,Model model) throws IOException, ParseException {

        JSONObject jsonObject = new JSONObject(jsonReceiver.getJSON());

        String stringDate = jsonObject.getString("Date");
        JSONObject valute = jsonObject.getJSONObject("Valute");
        JSONObject currencyNeeded = valute.getJSONObject(convertValue);

        model.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(stringDate.substring(0, stringDate.indexOf("T"))));
        model.setNominal(currencyNeeded.getInt("Nominal"));
        model.setName(currencyNeeded.getString("Name"));
        model.setValue(currencyNeeded.getDouble("Value"));
    }
}
