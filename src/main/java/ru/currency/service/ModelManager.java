package ru.currency.service;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Data
@Service
public class ModelManager {
    
    @Autowired
    private ModelFiller modelFiller;

    private Model currentModel;

    public ModelManager() {
        this.currentModel = new Model();
    }

    public void createModel(String currencyName) throws IOException, ParseException {
        modelFiller.fillModel(currencyName,this.currentModel);
    }
}
