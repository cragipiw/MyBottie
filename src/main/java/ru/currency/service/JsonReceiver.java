package ru.currency.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

@Service

public class JsonReceiver {
    public String getJSON() throws IOException {
        Scanner response = new Scanner((InputStream)new URL("https://www.cbr-xml-daily.ru/daily_json.js").getContent());
        StringBuilder result = new StringBuilder();
        while (response.hasNext()) {
            result.append(response.nextLine());
        }
        return result.toString();
    }
}
